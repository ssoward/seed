// Inline edit directive
var app = angular.module('myApp');
app.directive('inlineEdit', function($timeout) {
    return {
        scope: {
            model: '=inlineEdit',
            handleSave: '&onSave',
            handleCancel: '&onCancel',
            label: '@inlineLabel'
        },
        link: function (scope, elm, attr) {
            var previousValue;
            if(!scope.model){
                scope.editMode = true;
            }
            scope.edit = function () {
                scope.editMode = true;
                previousValue = scope.model;

                $timeout(function () {
                    elm.find('input')[0].focus();
                }, 0, false);
            };
            scope.save = function () {
                scope.editMode = false;
                scope.handleSave({value: scope.model});
            };
            scope.cancel = function () {
                scope.editMode = false;
                scope.model = previousValue;
                scope.handleCancel({value: scope.model});
            };
        },
        templateUrl: './js/directives/inline-edit.html'
    };
});
// On esc event
app.directive('onEsc', function() {
    return function(scope, elm, attr) {
        elm.bind('keydown', function(e) {
            if (e.keyCode === 27) {
                scope.$apply(attr.onEsc);
            }
        });
    };
});

// On enter event
app.directive('onEnter', function() {
    return function(scope, elm, attr) {
        elm.bind('keypress', function(e) {
            if (e.keyCode === 13) {
                scope.$apply(attr.onEnter);
            }
        });
    };
});
