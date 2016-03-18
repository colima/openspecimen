
angular.module('os.administrative.shipment.addedit', ['os.administrative.models', 'os.biospecimen.models'])
  .controller('ShipmentAddEditCtrl', function(
    $scope, $state, shipment, Shipment, 
    Institute, Site, Specimen, SpecimensHolder, Alerts, Util) {

    function init() {
      $scope.shipment = shipment;
      $scope.shipment.shipmentItems = shipment.shipmentItems || [];

      if (!shipment.id && angular.isArray(SpecimensHolder.getSpecimens())) {
        shipment.shipmentItems = getShipmentItems(SpecimensHolder.getSpecimens());
        SpecimensHolder.setSpecimens(null);
      }
      
      if (!shipment.shippedDate) {
        shipment.shippedDate = new Date();
      }

      loadInstitutes();
      loadSendingSites();
      setUserAndSiteList(shipment);
    }
    
    function loadInstitutes () {
      Institute.query().then(
        function (institutes) {
          $scope.instituteNames = Institute.getNames(institutes);
        }
      );
    }

    function loadSites(instituteName) {
      Site.listForInstitute(instituteName, true).then(
        function(sites) {
          $scope.sites = sites;
        }
      );
    }
    
    function loadSendingSites() {
      Site.list().then(
        function(sites) {
          $scope.sendingSites = sites;
        }
      );
    }

    function setUserFilterOpts(institute) {
      $scope.userFilterOpts = {institute: institute};
    }

    function setUserAndSiteList(shipment) {
      var instituteName = shipment.receivingInstitute;
      if (instituteName) {
        setUserFilterOpts(instituteName);
        loadSites(instituteName);
      }
    }

    function getShipmentItems(specimens) {
      return specimens.filter(
        function(specimen) {
          return specimen.available && specimen.availableQty > 0 && specimen.activityStatus == 'Active';
        }).map(
        function(specimen) {
          return {
            specimen: specimen
          };
        });
    }

    function saveOrUpdate(status) {
      var shipment = angular.copy($scope.shipment);
      shipment.status = status;
      shipment.$saveOrUpdate().then(
        function(savedShipment) {
          $state.go('shipment-detail.overview', {shipmentId: savedShipment.id});
        }
      );
    };

    $scope.onInstituteSelect = function(instituteName) {
      $scope.shipment.receivingSite = undefined;
      $scope.shipment.notifyUsers = [];

      loadSites(instituteName);
      setUserFilterOpts(instituteName);
    }

    $scope.onSiteSelect = function(siteName) {
      Site.getByName(siteName).then(
        function(site) {
          $scope.shipment.notifyUsers = site.coordinators;
        }
      );
    }

    $scope.addSpecimens = function(labels) {
      var param = {
        label: labels,
        sendSiteName: $scope.shipment.sendingSite,
        recvSiteName: $scope.shipment.receivingSite
      }

      return Specimen.listForShipment(param).then(
        function (specimens) {
          Util.appendAll($scope.shipment.shipmentItems, getShipmentItems(specimens));
          return true;
        }
      );
    }

    $scope.removeShipmentItem = function(shipmentItem) {
      var idx = shipment.shipmentItems.indexOf(shipmentItem);
      if (idx != -1) {
        shipment.shipmentItems.splice(idx, 1);
      }
    }

    $scope.ship = function() {
      saveOrUpdate('Shipped');
    }

    $scope.saveDraft = function() {
      saveOrUpdate('Pending');
    }

    init();
  });
