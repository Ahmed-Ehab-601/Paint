import React, { useState, useEffect } from "react";
import { Stage, Layer } from "react-konva";
import Shape from "./shapes";

const App = ({ type ,stroke,fill }) => {
  const [shapes, setShapes] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [shapeToAdd, setShapeToAdd] = useState(null);

  const addShape = (type, x, y,fill,stroke) => {
    const id = shapes.length + 1;
    const baseSize = 75;

    const newShape = {
      id: id.toString(),
      type,
      x,
      y,
      fill:fill,
      stroke: stroke,
      strokeWidth: 2,
      rotation: 0,
      radius: baseSize / 2,
      width: baseSize,
      height: baseSize * 1.5,
      radiusX: baseSize,
      radiusY: baseSize / 1.5,
      points: [
        0, -baseSize / 2,
        baseSize / 2, baseSize / 2,
        -baseSize / 2, baseSize / 2,
      ],
    };

    setShapes([...shapes, newShape]);
  };

  const handleCanvasClick = (e) => {
    const stage = e.target.getStage();

    // If clicked on the stage (not a shape), deselect all shapes
    if (e.target === stage) {
      setSelectedId(null);
    }

    // Add new shape if shapeToAdd is selected
    if (shapeToAdd) {
      const pointerPosition = stage.getPointerPosition();
      if (pointerPosition) {
        const { x, y } = pointerPosition;
        addShape(shapeToAdd, x, y,fill,stroke);
      }
      setShapeToAdd(null); // Reset shapeToAdd after adding the shape
    }
  };

  const handleShapeSelect = (shapeId) => {
    setSelectedId(shapeId);

    setShapes((prevShapes) => {
      const selectedShape = prevShapes.find((shape) => shape.id === shapeId);
      if (!selectedShape) return prevShapes;
      const updatedShapes = prevShapes.filter((shape) => shape.id !== shapeId);
      return [...updatedShapes, selectedShape];
    });
  };

  useEffect(() => {
    if (type) {
      setShapeToAdd(type);
    }
  }, [type]);

  const updateShape = (id, newAttrs) => {
    const updatedShapes = shapes.map((shape) =>
      shape.id === id ? newAttrs : shape
    );
    setShapes(updatedShapes);
  };

  return (
    <div style={{ position: "relative", width: "100%", height: "100%" }}>
      <Stage
        width={window.innerWidth - 790}
        height={window.innerHeight - 300}
        style={{ border: "1px solid red", position: "absolute", top: -10, left: -10, zIndex: 0 }}
        onMouseDown={handleCanvasClick}
        onTouchStart={handleCanvasClick}
      >
        <Layer>
          {shapes.map((shape) => (
            <Shape
              key={shape.id}
              shape={shape}
              isSelected={shape.id === selectedId}
              onSelect={() => handleShapeSelect(shape.id)}
              onChange={(newAttrs) => updateShape(shape.id, newAttrs)}
            />
          ))}
        </Layer>
      </Stage>
      type ="";
    </div>
  );
};

export default App;
