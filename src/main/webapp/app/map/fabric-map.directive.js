(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('fabricMap', fabricMap);

    fabricMap.$inject = ['$window', 'Player', 'Camera', 'mapConstants', 'ProjectManager', 'Project', 'FabricMapDialog'];

    /**
     * The controller for the map directive.
     * @param $window
     * @param Player
     * @param Camera
     * @param mapConstants
     * @param ProjectManager
     * @param Project
     * @returns {{restrict: string, scope: {map: string, selected: string, editable: string, highlight: string}, link: link, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function fabricMap($window, Player, Camera, mapConstants, ProjectManager, Project, FabricMapDialog) {
        var directive = {
            restrict: 'EA',
            scope: {
                map: '=map',
                selected: '=selected',
                editable: '=editable',
                highlight: '=highlight'
            },
            link: link,
            controller: 'MapEditorController',
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
            scope.vm.highlight = highlightEntities;
            scope.vm.hoverTarget = null;
            var label = new fabric.Text('', {
                fill: '#fff',
                fontFamily: 'Helvetica, Arial',
                fontSize: 14,
                name: 'label'
            });

            init();

            /**
             * Resize the canvas on window resize.
             */
            angular.element($window).bind('resize', resize);

            /**
             * Redraw the entities on update.
             */
            scope.$watch('vm.map', function () {
                draw(scope.vm.map.cameras, scope.vm.map.players);
            });

            scope.canvas.on('object:selected', onSelect);

            /**
             * Update the entity on the backend on object modification.
             */
            scope.canvas.on('object:modified', updateEntity);
            
            /**
             * Add entity on double click on the map at the clicked position.
             */
            scope.canvas.on('mouse:dblclick', function (options) {
                var position = getRelativePosition(options.e.offsetX, options.e.offsetY);
                addEntity(position);
            });

            /**
             * Move the label on moving the object.
             */
            scope.canvas.on('object:moving', function (options) {
                setLabel(options.target);
            });
            
            /**
             * Sets the label to the current target the mouse is hovering over.
             */
            scope.canvas.on('mouse:over', function (options) {
                var target = options.target;
                setLabel(target);
            });

            /**
             * Removes the label if the mouse leaves the target.
             */
            scope.canvas.on('mouse:out', function () {
                scope.vm.hoverTarget = null;
                label.setText('');
                scope.canvas.renderAll();
            });

            /**
             * Initializes the map with a canvas and loads the cameras and players.
             */
            function init() {
                ProjectManager.get().then(function (projectId) {
                    Project.get({id: projectId.data}, function (project) {
                        onLoadSuccess(project.map);
                    }, onLoadError);
                }, onLoadError);

                var canvas = new fabric.CanvasEx(element[0]);
                canvas.selection = false;
                scope.canvas = canvas;
                scope.canvas.add(label);

                var default_bg = "content/images/error.jpg";
                var error_bg = "content/images/error.jpg";

                function onLoadSuccess(map) {
                    if (map.backgroundImage) {
                        load(map.backgroundImage);
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
             * Resize the canvas based on the parent.
             */
            function resize() {
                var parent = element.parent().parent();
                var width = parent[0].clientWidth - (parent[0].offsetLeft);

                scope.canvas.setWidth(width);
                scope.canvas.setHeight(width / scope.aspectRatio);
                scope.canvas.backgroundImage.set({
                    width: scope.canvas.width,
                    height: scope.canvas.width / scope.aspectRatio
                });
                // scope.canvas.forEachObject(setDrawableProperties);
                scope.canvas.renderAll();
                scope.canvas.calcOffset();

                draw(scope.vm.map.cameras, scope.vm.map.players);
            }

            /**
             * Highlights the selected entities on the map.
             * @param selected The selected entities on the map
             */
            function highlightEntities(selected) {
                var entities = selected.map(function (entity) {
                    return entity.name;
                });
                scope.canvas.getObjects().forEach(function (item) {
                    if (!item.hasOwnProperty('entity')) {
                        return;
                    }
                    var opacity = mapConstants.nonhighlightOpacity;
                    
                    var fill = item.isPlayer ? mapConstants.defaultPlayerFill : mapConstants.defaultCameraFill;
                    
                    if (entities.indexOf(item.entity.name) > -1) {
                        opacity = 1.0;
                        fill = mapConstants.highlightFill;
                    }
                    item.setFill(fill);
                    item.animate('opacity', opacity, {
                        onChange: scope.canvas.renderAll.bind(scope.canvas),
                        duration: mapConstants.fadeDuration
                    });
                });
                scope.canvas.renderAll();
            }

            /**
             * Prepares the cameras and players for drawing and draws them to the map.
             * @param cameras the cameras to draw
             * @param players the players to draw
             */
            function draw(cameras, players) {
                var drawableCameras = cameras.map(transformCamera);
                var drawablePlayers = players.map(transformPlayer);
                scope.canvas.clear();
                scope.canvas.add(label);
                drawEntities(drawableCameras.concat(drawablePlayers));
            }

            /**
             * Draw an entity to the map.
             * @param entities the entities to draw
             */
            function drawEntities(entities) {
                var drawables = entities.map(setDrawableProperties);
                drawables.forEach(function (d) {
                    scope.canvas.add(d);
                });
                scope.canvas.renderAll();
            }

            /**
             * Sets the properties of the draw object.
             * @param drawable the object that is drawn
             * @returns {*}
             */
            function setDrawableProperties(drawable) {
                drawable.width = 15;
                drawable.height = 15;
                drawable.lockScalingX = true;
                drawable.lockScalingY = true;
                var position = getAbsolutePosition(drawable.x, drawable.y);
                drawable.left = position.x;
                drawable.top = position.y;

                if (scope.vm.editable === false) {
                    drawable.lockMovementX = true;
                    drawable.lockMovementY = true;
                    drawable.hasControls = false;
                }
                return drawable;
            }

            /**
             * Transforms a camera and sets its attributes.
             * @param camera the camera to transform
             * @returns {*}
             */
            function transformCamera(camera) {
                var cam = new fabric.Path(mapConstants.cameraSVG);
                cam.set({
                    x: camera.x,
                    y: camera.y,
                    angle: camera.angle,
                    scaleX: 0.3,
                    scaleY: 0.3,
                    padding: 10,
                    fill: mapConstants.defaultCameraFill,
                    entity: camera,
                    isCamera: true,
                    hasControls: true
                });
                cam.setControlsVisibility({
                    bl: false,
                    br: false,
                    mb: false,
                    ml: false,
                    mr: false,
                    mt: false,
                    tl: false,
                    tr: false,
                    mtr: true
                });
                return cam;
            }

            /**
             * Transforms a player and sets its attributes.
             * @param player the player to transform
             * @returns {*}
             */
            function transformPlayer(player) {
                var p = new fabric.Circle();
                p.set({
                    radius: 15,
                    x: player.x,
                    y: player.y,
                    scaleX: 0.8,
                    scaleY: 0.8,
                    padding: 5,
                    fill: mapConstants.defaultPlayerFill, 
                    stroke: 'white',
                    entity: player,
                    isPlayer: true,
                    hasControls: false
                });
                return p;
            }

            /**
             * Returns the absolute position of x and y coordinates on the map.
             * @param x the x position of the object
             * @param y the y position of the object
             * @returns {{}}
             */
            function getAbsolutePosition(x, y) {
                var currentWidth = scope.canvas.getWidth();
                var currentHeight = scope.canvas.getHeight();
                var position = {};
                position.x = (x / 100) * currentWidth;
                position.y = (y / 100) * currentHeight;
                return position;
            }

            /**
             * Returns the relative position of x and y coordinates on the map.
             * @param x the x position of the object
             * @param y the y position of the object
             * @returns {{}}
             */
            function getRelativePosition(x, y) {
                var currentWidth = scope.canvas.getWidth();
                var currentHeight = scope.canvas.getHeight();
                var position = {};
                position.x = (x / currentWidth) * 100;
                position.y = (y / currentHeight) * 100;
                return position;
            }

            /**
             * Updates an entity (camera or player) when it is moved on the map.
             * @param options the options that are changed
             */
            function updateEntity(options) {
                var target = options.target;
                if (target) {
                    var entity = target.entity;
                    var position = getRelativePosition(target.left, target.top);
                    if (isInRange(position.x) && isInRange(position.y)) {
                        entity.x = position.x;
                        entity.y = position.y;
                        entity.angle = target.angle;
                        onModify(target);
                    } else {
                        resize();
                    }
                }
            }

            /**
             * Displays the information of an object when it is selected.
             * @param options
             */
            function onSelect(options) {
                var target = options.target;
                setLabel(target);
                scope.$apply(function () {
                    scope.vm.selected = target.entity;
                });
            }

            /**
             * Updates the cameras and players on the backend when they are modified in the view.
             * @param target the camera or player that is updated
             */
            function onModify(target) {
                if (target.isCamera) {
                    Camera.update(target.entity);
                } else if (target.isPlayer) {
                    Player.update(target.entity);
                }
            }

            /**
             * Sets the label on the selected target.
             * @param target The target, which can be a camera or a player
             */
            function setLabel(target) {
                scope.vm.hoverTarget = target;
                label.set({
                    left: target.left + 20,
                    top: target.top + 10,
                    text: target.entity.name,
                    textBackgroundColor: 'rgba(0,0,0,0.3)'
                });
                label.bringToFront();
                scope.canvas.renderAll();
            }

            /**
             * Checks if a variable is between 0 and 100.
             * @param variable Variable to be range checked
             * @returns {boolean} True if it's in range
             */
            function isInRange(variable) {
                return variable >= 0 && variable <= 100;
            }

            function addEntity(position) {
                FabricMapDialog.chooseEntity(scope.vm.project.map, position.x, position.y).result.then(function () {
                    scope.vm.update();
                });
            }
        }
    }
})();

