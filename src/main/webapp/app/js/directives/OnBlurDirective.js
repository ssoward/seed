angular.module('myApp').directive('ngBlur', function() {
    return function( scope, elem, attrs ) {
        elem.bind('click', function() {
            scope.$apply(attrs.ngBlur);
        });
    };
});