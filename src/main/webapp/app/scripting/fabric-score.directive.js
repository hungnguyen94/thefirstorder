(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('fabricScore', fabricScore);

    fabricScore.$inject = ['$window', 'Script'];

    /**
     * The controller of the score directive.
     * @param $window
     * @param Script
     * @returns {{restrict: string, scope: {score: string}, link: link, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function fabricScore($window, Script) {
        var directive = {
            restrict: 'EA',
            scope: {
                score: '=score',
            },
            link: link,
            controller: 'ScriptingController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        /**
         * Links the controller with the directive.
         * @param scope the scope that the directive is called in
         * @param element the element that the directive is called in
         * @param attrs the attributes of the element that the directive is called in
         */
        function link(scope, element, attrs) {
            scope.canvas = {};

            init();

            angular.element($window).bind('resize', resize);

            /**
             * Initializes the script with a canvas and loads the cameras and players.
             */
            function init() {
                Script.get({id: 1}, onLoadSuccess, onLoadError);

                var canvas = new fabric.Canvas(element[0]);
                scope.canvas = canvas;

                var default_bg = "content/images/concertzaal.jpg";
                var error_bg = "content/images/concertzaal.jpg";

                function onLoadSuccess(script) {
                    if(script.score){
                        load(script.score);
                    } else {
                        load(default_bg);
                    }
                }

                function onLoadError() {
                    load(error_bg);
                }

                function load(bg_image) {
                    fabric.Image.fromURL(bg_image, function (img) {
                        scope.aspectRatio = img.width / img.height;
                        img.set({
                            width: canvas.width,
                            height: canvas.width / scope.aspectRatio,
                            originX: 'left',
                            originY: 'top'
                        });
                        canvas.setBackgroundImage(img, canvas.renderAll.bind(canvas));
                        resize();
                    });
                }
            }

            /**
             * Resize the canvas based on the timeline.
             */
            function resize() {
                var parent = element.parent().parent();
                var width = parent[0].clientWidth - 30;

                scope.canvas.setWidth(width);
                scope.canvas.setHeight(width / scope.aspectRatio);
                scope.canvas.backgroundImage.set({
                    width: scope.canvas.width,
                    height: scope.canvas.width / scope.aspectRatio
                });
                
                scope.canvas.renderAll();
                scope.canvas.calcOffset();
            }
        }
    }
})();

