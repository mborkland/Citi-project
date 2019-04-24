app.controller('CreateController', ['$scope', '$http', '$state', '$window', '$uibModal', 'tableService',
function($scope, $http, $state, $window, $uibModal, tableService) {
    $scope.cxBuDetails = $scope.cxBuDetails || {};
    $scope.txBuDetails = $scope.txBuDetails || {};
    $scope.soeidData = $scope.soeidData || {};
    $scope.pattern = "^[a-zA-Z0-9.:;, ]*$";

    $scope.duplicateRows = [];

    $scope.isFormValid = function(ngForm) {
        $scope.$broadcast('$validate');
        if(!ngForm.$invalid) {
            return true;
        } else {
            ngForm.$dirty = true;
            for(var i in ngForm) {
                if(ngForm[i] && ngForm[i].hasOwnProperty && ngForm[i].hasOwnProperty('$dirty')) {
                    ngForm[i].$dirty = true;
                }
            }
        }
    }

    $scope.nextButton = function(ngForm) {
        if($scope.isFormValid(ngForm)) {
            if($state.current.name === 'create-cx.section1')
                $state.go('create-cx.section2');
            else if($state.current.name === 'create-cx.section2')
                $state.go('create-cx.section3');
            else if($state.current.name === 'create-tx.section1')
                $state.go('create-tx.section2');
            else if($state.current.name === 'create-tx.section2')
                $state.go('create-tx.section3');
            else if($state.current.name === 'create-tx.section3')
                $state.go('create-tx.section4');
            else if($state.current.name === 'create-tx.section4')
                $state.go('create-tx.section5');
            else if($state.current.name === 'create-tx.section5')
                $state.go('create-tx.section6');
            else if($state.current.name === 'create-tx.section6')
                $state.go('create-tx.section7');
        }
        else {
            $state.go($state.current.name);
        }
    }

    $scope.forceCreate = function() {
        if ($scope.recordType === 'CUSTOMER') {
            $scope.createCx(true);
        } else {
            $scope.createTx(true);
        }
    };

    $scope.createCx = function(force) {
        var data = {
            buDetails: $scope.cxBuDetails,
            soeid: $scope.soeidData.soeid
        };

        if ($scope.isFormValid($scope['section3'])) {
            if (force) {
                $http({
                    method: 'POST',
                    url: '/create-cx',
                    data: data
                }).then(function (response) {
                    alert(response.data.result);
                    $state.go('read');
                }, function (error) {
                    console.log(error);
                    alert('There was an error');
                });
            } else {
                $scope.recordType = 'CUSTOMER';
                $http({
                    method: 'POST',
                    url: '/duplicate-cx',
                    data: $scope.cxBuDetails
                }).then(function (response) {
                    if (response.data.length) {
                        $scope.duplicateRows.length = 0;
                        angular.forEach(response.data, function (value, key) {
                            $scope.duplicateRows.push(convertResponseData(value));
                        });
                        showDuplicateModal($scope.forceCreate, $scope.openDuplicateWindow);
                    } else {
                        $scope.createCx(true);
                    }
                }, function (error) {
                    console.log(error);
                });
            }
        }
    };

    $scope.createTx = function(force) {
        var data = {
            buDetails: $scope.txBuDetails,
            soeid: $scope.soeidData.soeid
        };

        if ($scope.isFormValid($scope['section7'])) {
            if (force) {
                $http({
                    method: 'POST',
                    url: '/create-tx',
                    data: data
                }).then(function (response) {
                    alert(response.data.result);
                    $state.go('read');
                }, function (error) {
                    console.log(error);
                    alert('There was an error');
                });
            } else {
                $scope.recordType = 'TRANSACTION';
                $http({
                    method: 'POST',
                    url: '/duplicate-tx',
                    data: $scope.txBuDetails
                }).then(function (response) {
                    if (response.data.length) {
                        $scope.duplicateRows.length = 0;
                        angular.forEach(response.data, function (value, key) {
                            $scope.duplicateRows.push(convertResponseData(value));
                        });
                        showDuplicateModal($scope.forceCreate, $scope.openDuplicateWindow);
                    } else {
                        $scope.createTx(true);
                    }
                }, function (error) {
                    console.log(error);
                });
            }
        }
    };

    $scope.openDuplicateWindow = function() {
        $window.parentScope = $scope;
        $window.open('views/windows/duplicate-records.html', 'Duplicate Records', 'width=1000,height=600');
    };

    function showDuplicateModal(forceCreate, openDuplicateWindow) {
        $scope.numDuplicates = $scope.duplicateRows.length;
        var duplicateModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/duplicate.html',
            controller: function ($scope) {
                $scope.forceCreate = function() {
                    $scope.$close();
                    forceCreate();
                };
                $scope.openDuplicateWindow = function() {
                    $scope.$close();
                    openDuplicateWindow();
                }
            },
            scope: $scope,
            size: 'md'
        });
    }

    $scope.showHistoryModal = function(id) {
        var history = getHistory(id);
        var historyModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/history.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    historyData: history
                }
            }
        });
    };

    function convertResponseData(record) {
        if ($scope.recordType === 'CUSTOMER') {
            return tableService.convertCxData(record, false);
        } else {
            return tableService.convertTxData(record, false);
        }
    }
}]);