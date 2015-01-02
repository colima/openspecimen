
angular.module('os.biospecimen.cp.specimens', ['os.biospecimen.models'])
  .controller('CpSpecimensCtrl', function(
    $scope, $state, $stateParams, $filter,
    cp, events, specimenRequirements,
    Specimen, SpecimenRequirement, PvManager) {

    if (!$stateParams.eventId && !!events && events.length > 0) {
      $state.go('cp-detail.specimen-requirements', {eventId: events[0].id});
      return;
    }

    function init() {
      $scope.cp = cp;
      $scope.events = events;
      $scope.eventId = $stateParams.eventId;

      $scope.specimenRequirements = Specimen.flatten(specimenRequirements);

      $scope.view = 'list_sr';
      $scope.sr = {};
    }

    function addToSrList(sr) {
      specimenRequirements.push(sr);
      specimenRequirements = $filter('orderBy')(specimenRequirements, ['type', 'id']);
      $scope.specimenRequirements = Specimen.flatten(specimenRequirements);
    };

    var pvsLoaded = false;
    function loadPvs() {
      if (pvsLoaded) {
        return;
      }

      $scope.specimenClasses = PvManager.getPvs('specimen-class');
      $scope.specimenTypes = [];

      $scope.$watch('sr.specimenClass', function(newVal, oldVal) {
        if (!newVal || newVal == oldVal) {
          return;
        }

        $scope.specimenTypes = PvManager.getPvsByParent('specimen-class', newVal);
        $scope.sr.type = '';
      });

      $scope.anatomicSites = PvManager.getPvs('anatomic-site');
      $scope.lateralities = PvManager.getPvs('laterality');
      $scope.pathologyStatuses = PvManager.getPvs('pathology-status');
      $scope.storageTypes = PvManager.getPvs('storage-type');
      $scope.collectionProcs = PvManager.getPvs('collection-procedure');
      $scope.collectionContainers = PvManager.getPvs('collection-container');
      pvsLoaded = true;
    }

    $scope.openSpecimenNode = function(sr) {
      sr.isOpened = true;
    };

    $scope.closeSpecimenNode = function(sr) {
      sr.isOpened = false;
    };

    $scope.showAddSr = function() {
      $scope.view = 'addedit_sr';
      $scope.sr = new SpecimenRequirement({eventId: $scope.eventId});
      loadPvs();
    };

    $scope.revertEdit = function() {
      $scope.view = 'list_sr';
      $scope.parentSr = null;
    };

    $scope.createSr = function() {
      $scope.sr.$saveOrUpdate().then(
        function(result) {
          addToSrList(result);
          $scope.view = 'list_sr';
        }
      );
    };

    ////////////////////////////////////////////////
    //
    //  Aliquot logic
    //
    ////////////////////////////////////////////////
    $scope.showCreateAliquots = function(sr) {
      $scope.parentSr = sr;
      $scope.view = 'addedit_aliquot';
      $scope.aliquot = {};
      loadPvs();
    };

    $scope.createAliquots = function() {
      alert(JSON.stringify($scope.aliquot));
      $scope.aliquot = {};
      $scope.parentSr = undefined;
      $scope.view = 'list_sr';
    };

    init();
  });
