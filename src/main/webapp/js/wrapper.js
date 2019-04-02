/*
MIT License

Copyright (c) 2017 Guruprakash Rajakkannu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

(function() {
  angular
    .module('bootstrap4-modal', [])
  
  angular
    .module('bootstrap4-modal')
    .service('$bootstrap4Modal', bootstrap4ModalService);

  bootstrap4ModalService.$inject = [
    '$compile', 
    '$rootScope', 
    '$templateRequest', 
    '$templateCache', 
    '$q',
    '$controller'
  ];
    
  function bootstrap4ModalService($compile, $rootScope, $templateRequest, $templateCache, $q, $controller) {
    var el;
    var modalDeferred;
    
    var getTemplate = function(tmplUrl) {
      var deferred = $q.defer();
      var template = $templateCache.get(tmplUrl);
      if(!template) {
        $templateRequest(tmplUrl).then(function(response) {
          deferred.resolve(response);
        }, function(error) {
          deferred.reject(error);
        });
      } else {
        deferred.resolve(template);
      }
      return deferred.promise;
    }
    
    /**
     * Opens bootstarp modal with templateUrl, controller and modalData.
     * 
     * @param {string} tmplUrl - template Url.
     * @param {string} controllerName - controller to bind to modal.
     * @param {*} [modalData] - this additional data will get injected as $scope.modalData.
     * 
     * @return {object} promise which will be resolved or rejected while closing the modal.
     */
    var show = function(tmplUrl, controllerName, modalData) {
      hide();
      el = undefined;
      modalDeferred = $q.defer();
      getTemplate(tmplUrl).then(function(template) {
        var $scope = $rootScope.$new();
        if(modalData) {
          $scope.modalData = modalData;
        }
        var ctrl = $controller(controllerName, {
          $scope: $scope
        });
        var compiledData = $compile(template)($scope);
        compiledData.controller = ctrl;
        el = $(compiledData);
        el.appendTo('body');
        el.modal('show');
      }, function(error) {
        modalDeferred.reject(error);
      });
      return modalDeferred.promise;
    }
    
    var close = function() {
      var deferred = $q.defer();
      el.on('hidden.bs.modal', function (e) {
        el.remove();
        deferred.resolve();
      });
      el.modal('hide');
      return deferred.promise;
    }
    
    /**
     * Closes the modal and resolves the modal promise with given data.
     * 
     * @param {*} data
     */
    var hide = function(data) {
      if(el && modalDeferred) {
        close().then(function() {
          modalDeferred.resolve(data);
        });
      }
    }
    
    /**
     * Closes the modal and rejects the modal promise with given data.
     * 
     * @param {*} data
     */
    var cancel = function(data) {
      if(el && modalDeferred) {
        close().then(function() {
          modalDeferred.reject(data);
        });
      }
    }
    
    return {
      show: show,
      hide: hide,
      cancel: cancel
    }
  }
})();
