
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

    function getOccupantName(container, occupant) {
      if (occupant.occuypingEntity == 'specimen' && container.cellDisplayProp == 'SPECIMEN_PPID') {
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
          return getOccupantName(container, occupant);
        },
        occupantDisplayHtml: function(occupant) {
          if (occupant.occuypingEntity == 'specimen' && !!occupant.occupantProps) {
            var icon = angular.element('<div class="slot-icon"/>').append(
              angular.element('<os-specimen-icon ' +
                'specimen-class="\'' + occupant.occupantProps.specimenClass + '\'" ' +
                'type="\'' + occupant.occupantProps.type + '\'"/>')
            );

            var label = angular.element('<div class="slot-label"/>')
              .append(getOccupantName(container, occupant));

            return angular.element('<div/>').append(icon).append(label);
          } else {
            return angular.element('<span class="slot-desc"/>').append(occupant.occupyingEntityName);
          }
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
