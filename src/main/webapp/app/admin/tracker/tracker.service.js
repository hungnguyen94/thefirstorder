(function() {
    'use strict';
    /* globals SockJS, Stomp */

    angular
        .module('thefirstorderApp')
        .factory('JhiTrackerService', JhiTrackerService);

    JhiTrackerService.$inject = ['$rootScope', '$window', '$q', '$localStorage'];

    /**
     * Service to support websockets.
     * @param $rootScope the scope of the root
     * @param $window this window
     * @param $q angular functions
     * @param $localStorage the local storage
     * @returns {{connect: connect, disconnect: disconnect, receive: receive, sendActivity: sendActivity, sendPrevious: sendPrevious, sendNext: sendNext, subscribe: subscribe, unsubscribe: unsubscribe}}
     * @constructor
     */
    function JhiTrackerService ($rootScope, $window, $q, $localStorage) {
        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();
        var alreadyConnectedOnce = false;

        var service = {
            connect: connect,
            disconnect: disconnect,
            receive: receive,
            sendActivity: sendActivity,
            sendPrevious: sendPrevious,
            sendNext: sendNext,
            subscribe: subscribe,
            sendCurrent: sendCurrent,
            unsubscribe: unsubscribe
        };

        return service;

        /**
         * Connect to a websocket.
         */
        function connect () {
            //building absolute path so that websocket doesnt fail when deploying with a context path
            var loc = $window.location;
            var url = '//' + loc.host + loc.pathname + 'websocket/tracker';
            /*jshint camelcase: false */
            var authToken = angular.fromJson($localStorage.authenticationToken).access_token;
            url += '?access_token=' + authToken;
            var socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            var stateChangeStart;
            var headers = {};
            stompClient.connect(headers, function() {
                connected.resolve('success');
                sendActivity();
                if (!alreadyConnectedOnce) {
                    stateChangeStart = $rootScope.$on('$stateChangeStart', function () {
                        sendActivity();
                    });
                    alreadyConnectedOnce = true;
                }
            });
            $rootScope.$on('$destroy', function () {
                if(angular.isDefined(stateChangeStart) && stateChangeStart !== null){
                    stateChangeStart();
                }
            });
        }

        /**
         * Disconnect from the websocket.
         */
        function disconnect () {
            if (stompClient !== null) {
                stompClient.disconnect();
                stompClient = null;
            }
        }

        /**
         * Receive a message.
         * @returns {Promise}
         */
        function receive () {
            return listener.promise;
        }

        /**
         * Send an activity to a client.
         */
        function sendActivity() {
            if (stompClient !== null && stompClient.connected) {
                stompClient
                    .send('/topic/activity',
                    {},
                    angular.toJson({'page': 'none'}));
            }
        }

        /**
         * Send the message 'previous cue' to the client.
         */
        function sendPrevious(current) {
            if (stompClient !== null && stompClient.connected) {
                stompClient
                    .send('/topic/activity',
                        {},
                        angular.toJson({'page': 'previous', 'current': current}));
            }
        }

        /**
         * Send the message 'next cue' to the client.
         */
        function sendNext(current) {
            if (stompClient !== null && stompClient.connected) {
                stompClient
                    .send('/topic/activity',
                        {},
                        angular.toJson({'page': 'next', 'current': current}));
            }
        }

	/**
         * Send the message 'current cue' to the client.
         */
        function sendCurrent(current) {
            if (stompClient !== null && stompClient.connected) {
                stompClient
                    .send('/topic/activity',
                        {},
                        angular.toJson({'page': 'none', 'current': current}));
            }
        }

        /**
         * Subscribe to a sender to start receiving messages.
         */
        function subscribe () {
            connected.promise.then(function() {
                subscriber = stompClient.subscribe('/topic/tracker', function(data) {
                    listener.notify(angular.fromJson(data.body));
                });
            }, null, null);
        }

        /**
         * Unsubscribe from a sender.
         */
        function unsubscribe () {
            if (subscriber !== null) {
                subscriber.unsubscribe();
            }
            listener = $q.defer();
        }
    }
})();
