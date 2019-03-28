app.controller('BootstrapCtrl', function($scope) {});

app.directive('scopedcss', function($http, $compile) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            // Fetch bootstrap css (you should cache it instead of requesting it everytime)
            $http({
                method: 'GET',
                url: 'css/bootstrap.min.3.3.7.css'
            }).then(function successCallback(response) {
                // Create a <style scoped> tag with the CSS content
                var cssTag = angular.element('<style scoped>' + response.data + '</style>');

                // Insert the tag inside the element
                element.prepend(cssTag);

                // Call the process() method defined by scoper lib
                // It will parse the CSS and prefix it with a unique ID
                // It will also add the same ID to the element
                window.process();
            });
        }
    }
});