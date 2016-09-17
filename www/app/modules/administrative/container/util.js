
angular.module('os.administrative.container.util', ['os.common.box'])
  .factory('ContainerUtil', function(BoxLayoutUtil, NumberConverterUtil) {

    function createSpmnPos(container, label, x, y, oldOccupant) {
      return {
        occuypingEntity: 'specimen', 
        occupyingEntityName: label,
        posOne: NumberConverterUtil.fromNumber(container.columnLabelingScheme, x),
        posTwo: NumberConverterUtil.fromNumber(container.rowLabelingScheme, y),
        posOneOrdinal: x,
        posTwoOrdinal: y,
        oldOccupant: oldOccupant
      };
    }

    function getOccupantName(occupant, container) {
      if (occupant.occuypingEntity == 'specimen' && !!occupant.occupantProps &&
        container.calcSpecimenDisplayProp == 'PPID') {
        return occupant.occupantProps.ppid;
      }

      return occupant.occupyingEntityName;
    }

    function getOpts(container, allowClicks, showAddMarker) {
      return {
        box: {
          instance             : container,
          row                  : function(occupant) { return occupant.posTwoOrdinal; },
          column               : function(occupant) { return occupant.posOneOrdinal; },
          numberOfRows         : function() { return container.noOfRows; },
          numberOfColumns      : function() { return container.noOfColumns; },
          positionLabelingMode : function() { return container.positionLabelingMode; },
          rowLabelingScheme    : function() { return container.rowLabelingScheme; },
          columnLabelingScheme : function() { return container.columnLabelingScheme; },
          occupantClick        : function() { /* dummy method to make box allow cell clicks */ }
        },

        occupants: [],
        occupantName: function(occupant) {
          return getOccupantName(occupant, container);
        },
        occupantDisplayHtml: function(occupant, cellDesc) {
          if (occupant.occuypingEntity == 'specimen' && !!occupant.occupantProps) {
            // Removed existing class to show icon and label separately
            cellDesc.removeAttr('class');

            var icon = angular.element('<div class="slot-icon"/>');
            icon.append(angular.element('<os-specimen-icon ' +
              'specimen-class="\'' + occupant.occupantProps.specimenClass +'\'" ' +
              'type="\'' + occupant.occupantProps.type + '\'"/>'));

            var label = $("<div class='slot-label'/>");
            label.append(getOccupantName(occupant, container));
            return cellDesc.append(icon).append(label);
          }

          return occupant.occupyingEntityName;
        },
        allowClicks: allowClicks,
        isVacatable: function(occupant) {
          return occupant.occuypingEntity == 'specimen';
        },
        createCell: function(label, x, y, existing) {
          return createSpmnPos(container, label, x, y, existing);
        },
        onAddEvent: showAddMarker ? function() {} : undefined
      };
    }

    return {
      fromOrdinal: NumberConverterUtil.fromNumber,

      toNumber: NumberConverterUtil.toNumber,

      getOpts: getOpts,

      assignPositions: function(container, occupancyMap, inputLabels, vacateOccupants) {
        var opts = getOpts(container, false);
        opts.occupants = occupancyMap;

        var result = BoxLayoutUtil.assignCells(opts, inputLabels, vacateOccupants);
        return {map: result.occupants, noFreeLocs: result.noFreeLocs};
      }
    };
  });
