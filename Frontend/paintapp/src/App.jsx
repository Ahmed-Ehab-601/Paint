import React, { useState, useEffect } from "react";
import { Stage, Layer, Line } from "react-konva"; // Added Line for preview rendering
import Shape from "./shapes"; // You should define the Shape component separately

const App = ({ type, stroke, fill }) => {
  const [shapes, setShapes] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [shapeToAdd, setShapeToAdd] = useState(null);
  
  const [linePoints, setLinePoints] = useState([]);
  const [mousePosition, setMousePosition] = useState(null);

  const addShape = (type, x, y, fill, stroke, points) => {
    const id = shapes.length + 1;
    const baseSize = 75;

    const newShape = {
      id: id.toString(),
      type,
      x,
      y,
      fill,
      stroke,
      strokeWidth: 2,
      rotation: 0,
      radius: baseSize / 2,
      width: baseSize,
      height: baseSize * 1.5,
      radiusX: baseSize,
      radiusY: baseSize / 1.5,
      points: type === "line" ? points : [
        0, -baseSize / 2, baseSize / 2, baseSize / 2, -baseSize / 2, baseSize / 2,
      ],
    };

    setShapes([...shapes, newShape]);
  };

  const handleMouseMove = (e) => {
    const stage = e.target.getStage();
    const pointerPosition = stage.getPointerPosition();
    if (!pointerPosition) return;

    setMousePosition({ x: pointerPosition.x, y: pointerPosition.y });
  };

  useEffect(() => {
    setLinePoints([]);
    setMousePosition(null);
  }, [shapeToAdd]);

  const handleCanvasClick = (e) => {
    const stage = e.target.getStage();

    if (e.target === stage) {
      setSelectedId(null);
    }

    const pointerPosition = stage.getPointerPosition();
    if (!pointerPosition) return;

    const { x, y } = pointerPosition;

    if (shapeToAdd === "line") {
      setLinePoints((prevPoints) => {
        const updatedPoints = [...prevPoints, x, y];

        if (updatedPoints.length === 4) {
          addShape("line", 0, 0, fill, stroke, updatedPoints);
          setMousePosition(null);
          setShapeToAdd(null);
          return [];
        }

        return updatedPoints;
      });
      return;
    }

    if (shapeToAdd) {
      addShape(shapeToAdd, x, y, fill, stroke);
      setShapeToAdd(null);
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
    if (type !== "") {
      setShapeToAdd(type);
    }
  }, [type]);

  const updateShape = (id, newAttrs) => {
    const updatedShapes = shapes.map((shape) =>
      shape.id === id ? newAttrs : shape
    );
    setShapes(updatedShapes);
  };

  function FullScreenStage({ shapeToAdd, handleCanvasClick, handleMouseMove }) {
    const [dimensions, setDimensions] = useState({
      width: window.innerWidth,
      height: window.innerHeight,
    });

    useEffect(() => {
      const handleResize = () => {
        setDimensions({
          width: window.innerWidth,
          height: window.innerHeight,
        });
      };

      window.addEventListener("resize", handleResize);
      return () => window.removeEventListener("resize", handleResize);
    }, []);

    return (
      <div style={{ position: "relative", width: "100%", height: "100%" }}>
        <Stage
          width={dimensions.width}
          height={dimensions.height}
          style={{
            border: "1px solid red",
            position: "fixed",
            top: 0,
            left: 0,
            zIndex: 0,
          }}
          onMouseDown={handleCanvasClick}
          onTouchStart={handleCanvasClick}
          onMouseMove={shapeToAdd === "line" ? handleMouseMove : null}
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

            {shapeToAdd === "line" && linePoints.length === 2 && mousePosition && (
              <Line
                points={[...linePoints, mousePosition.x, mousePosition.y]}
                stroke="black"
                strokeWidth={2}
              />
            )}
          </Layer>
        </Stage>
      </div>
    );
  }

  return <FullScreenStage {...{ shapeToAdd, handleCanvasClick, handleMouseMove }} />;
};

export default App;
