/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.android.apps.mytracks.maps;

import com.google.android.apps.mytracks.MapOverlay.CachedLocation;
import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;

import android.graphics.Path;

import java.util.List;

/**
 * Tests for the {@link SingleColorTrackPathPainter}.
 * 
 * @author Youtao Liu
 */
public class SingleColorTrackPathPainterTest extends TrackPathPainterTestCase {
  private SingleColorTrackPathPainter singleColorTrackPathPainter;
  private Path pathMock;
  private static final int NUMBER_OF_LOCATIONS = 100;

  /**
   * Tests the
   * {@link SingleColorTrackPathPainter#updatePath(com.google.android.maps.Projection, android.graphics.Rect, int, Boolean, List, Path)}
   * method when all locations are valid.
   */
  public void testUpdatePath_AllValidLocation() {
    initialTrackPathDescriptorMock();
    List<CachedLocation> points = createCachedLocations(NUMBER_OF_LOCATIONS, true, -1);
    // Gets a random number from 1 to numberOfLocations.
    int startLocationIdx = (int) (1 + (NUMBER_OF_LOCATIONS - 1) * Math.random());

    for (int i = 0; i < NUMBER_OF_LOCATIONS - startLocationIdx; i++) {
      pathMock.lineTo(0, 0);
    }

    AndroidMock.replay(pathMock);
    singleColorTrackPathPainter.updatePath(myTracksOverlay.getMapProjection(mockView),
        myTracksOverlay.getMapViewRect(mockView), startLocationIdx, true, points, pathMock);
    AndroidMock.verify(pathMock);
  }

  /**
   * Tests the
   * {@link SingleColorTrackPathPainter#updatePath(com.google.android.maps.Projection, android.graphics.Rect, int, Boolean, List, Path)}
   * method when all locations are invalid.
   */
  public void testUpdatePath_AllInvalidLocation() {
    initialTrackPathDescriptorMock();
    List<CachedLocation> points = createCachedLocations(NUMBER_OF_LOCATIONS, false, -1);
    // Gets a random number from 1 to numberOfLocations.
    int startLocationIdx = (int) (1 + (NUMBER_OF_LOCATIONS - 1) * Math.random());
    AndroidMock.replay(pathMock);
    singleColorTrackPathPainter.updatePath(myTracksOverlay.getMapProjection(mockView),
        myTracksOverlay.getMapViewRect(mockView), startLocationIdx, true, points, pathMock);
    AndroidMock.verify(pathMock);
  }

  /**
   * Tests the
   * {@link SingleColorTrackPathPainter#updatePath(com.google.android.maps.Projection, android.graphics.Rect, int, Boolean, List, Path)}
   * method when there are three segment.
   */
  public void testUpdatePath_ThreeSegments() {
    initialTrackPathDescriptorMock();
    // First segment.
    List<CachedLocation> points = createCachedLocations(NUMBER_OF_LOCATIONS, true, -1);
    points.addAll(createCachedLocations(1, false, -1));
    // Second segment.
    points.addAll(createCachedLocations(NUMBER_OF_LOCATIONS, true, -1));
    points.addAll(createCachedLocations(1, false, -1));
    // Third segment.
    points.addAll(createCachedLocations(NUMBER_OF_LOCATIONS, true, -1));
    // Gets a random number from 1 to numberOfLocations.
    int startLocationIdx = (int) (1 + (NUMBER_OF_LOCATIONS - 1) * Math.random());
    for (int i = 0; i < NUMBER_OF_LOCATIONS - startLocationIdx; i++) {
      pathMock.lineTo(0, 0);
    }
    pathMock.moveTo(0, 0);
    for (int i = 0; i < NUMBER_OF_LOCATIONS - 1; i++) {
      pathMock.lineTo(0, 0);
    }
    pathMock.moveTo(0, 0);
    for (int i = 0; i < NUMBER_OF_LOCATIONS - 1; i++) {
      pathMock.lineTo(0, 0);
    }

    AndroidMock.replay(pathMock);
    singleColorTrackPathPainter.updatePath(myTracksOverlay.getMapProjection(mockView),
        myTracksOverlay.getMapViewRect(mockView), startLocationIdx, true, points, pathMock);
    AndroidMock.verify(pathMock);
  }

  /**
   * Initials a mocked TrackPathDescriptor object.
   */
  @UsesMocks(Path.class)
  private void initialTrackPathDescriptorMock() {
    pathMock = AndroidMock.createStrictMock(Path.class);
    singleColorTrackPathPainter = new SingleColorTrackPathPainter(getContext());
  }

}
