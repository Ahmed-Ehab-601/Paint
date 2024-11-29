import React, { useState, useEffect } from "react";
import { Stage, Layer, Line } from "react-konva"; // Added Line for preview rendering
import axios from "axios"; // Import axios for HTTP requests
import Shape from "./shapes"; // Define Shape component separately

const baseURL = "http://localhost:8080/shape"

const App = ({ type, stroke, fill, action ,loadedShapes}) => {
  const [shapes, setShapes] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [shapeToAdd, setShapeToAdd] = useState(null);

  const [linePoints, setLinePoints] = useState([]);
  const [mousePosition, setMousePosition] = useState(null);

  // Create shape function with API call
  const addShape = async (type, x, y, fill, stroke, points) => {
    const shapeDetails = {
      type,
      x,
      y,
      fill,
      stroke,
      points: type === "line" ? points : null,
    };
  
    try {
      const response = await axios.post('http://localhost:8080/shape/create', shapeDetails);
  
      if (response.status === 200) {
        const newShape = response.data;
        setShapes((prevShapes) => [...prevShapes, newShape]);
      } else {
        console.error("Failed to add shape: ", response);
      }
    } catch (error) {
      if (error.response) {
        console.error("Error response from server:", error.response);
      } else if (error.request) {
        console.error("Error with request:", error.request);
      } else {
        console.error("Error in setting up request:", error.message);
      }
    }
  };
  useEffect(()=> {
    switch(action){
      case "delete":
        deleteShape();
        break;
      case "copy":
        copyShape();
        break;
      case "undo":
        undo();
        break;
      case "redo":
        redo();
        break;
    }
  },[action])

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
  useEffect(()=> {
    setShapes(loadedShapes)
  },[loadedShapes]);

  useEffect(() => {
    if (type !== "") {
      setShapeToAdd(type);
    }
  }, [type]);

  const updateShape = async (id, newAttrs) => {
    const updatedShapes = shapes.map((shape) =>
      shape.id === id ? { ...shape, ...newAttrs } : shape
    );
    const updatedShape = updatedShapes.find((shape) => shape.id === id);
    if(updatedShape === null)
    {
      console.error("error in finding shape in editing");
      return;
    }
    setShapes(updatedShapes);
    try {
      const response = await axios.put(`http://localhost:8080/shape/edit/${id}`, updatedShape);
  
      if (response.status === 200) {
        console.log("Shape updated successfully in the database.");
      } else {
        console.error("Unexpected response status:", response.status);
      }
    } catch (error) {
      if (error.response) {
        console.error("Error response from server:", error.response);
      } else if (error.request) {
        console.error("Error with request:", error.request);
      } else {
        console.error("Error in setting up request:", error.message);
      }
    }
  };

  const deleteShape = async () => {
    if (selectedId != null) {
      setShapes((prevShapes) => prevShapes.filter((shape) => shape.id !== selectedId));
      try {
        const response = await axios.delete(`http://localhost:8080/${selectedId}`);
        if (response.status === 200) {
          console.log("deletion confirmed for shapeId" + selectedId)
        } else {
          console.error("Failed to delete shape: ", response);
        }
      } catch (error) {
        if (error.response) {
          console.error("Error response from server:", error.response);
        } else if (error.request) {
          console.error("Error with request:", error.request);
        } else {
          console.error("Error in setting up request:", error.message);
        }
      }
      setSelectedId(null);
    }
  }

  const copyShape = async () => {
    if (selectedId != null) {
      try {
        const response = await axios.put(`http://localhost:8080/copy/${selectedId}`);
        if (response.status === 200) {
          const copiedShape = response.data;
          setShapes((prevShapes) => [...prevShapes, copiedShape]);
          console.log("copying confirmed for shapeId" + selectedId)
        } else {
          console.error("Failed to copy shape: ", response, " bad request");
        }
      } catch (error) {
        if (error.response) {
          console.error("Error response from server:", error.response);
        } else if (error.request) {
          console.error("Error with request:", error.request);
        } else {
          console.error("Error in setting up request:", error.message);
        }
      }
    }
  }

  const undo = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/shape/undo/`);
      if (response.status === 200) {
        const { type, shapeDTO } = response.data;
  
        switch (type) {
          case "edit":
            {
              // Update the shape with the new data from shapeDTO
              const updatedShapes = shapes.map((shape) =>
                shape.id === shapeDTO.id ? shapeDTO : shape
              );
              setShapes(updatedShapes); // Update the state with the modified array
            }
            break;
  
          case "create":
            {
              // Add the new shape to the array with its new id
              setShapes((prevShapes) => [...prevShapes, shapeDTO]);
            }
            break;
  
          case "delete":
            {
              // Remove the shape using the given shapeDTO id
              const updatedShapes = shapes.filter((shape) => shape.id !== shapeDTO.id);
              setShapes(updatedShapes); // Update the state after deletion
            }
            break;
  
          default:
            break;
        }
  
        console.log(`Undo operation successful for shapeId ${selectedId}`);
      } else {
        console.error("Failed to perform undo operation: ", response, " bad request");
      }
    } catch (error) {
      if (error.response) {
        console.error("Error response from server:", error.response);
      } else if (error.request) {
        console.error("Error with request:", error.request);
      } else {
        console.error("Error in setting up request:", error.message);
      }
    }
  };

  const redo = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/shape/redo/`);
      if (response.status === 200) {
        const { type, shapeDTO } = response.data;
  
        switch (type) {
          case "edit":
            {
              // Update the shape with the new data from shapeDTO
              const updatedShapes = shapes.map((shape) =>
                shape.id === shapeDTO.id ? shapeDTO : shape
              );
              setShapes(updatedShapes); // Update the state with the modified array
            }
            break;
  
          case "create":
            {
              // Add the new shape to the array with its new id
              setShapes((prevShapes) => [...prevShapes, shapeDTO]);
            }
            break;
  
          case "delete":
            {
              // Remove the shape using the given shapeDTO id
              const updatedShapes = shapes.filter((shape) => shape.id !== shapeDTO.id);
              setShapes(updatedShapes); // Update the state after deletion
            }
            break;
  
          default:
            break;
        }
  
        console.log(`Undo operation successful for shapeId ${selectedId}`);
      } else {
        console.error("Failed to perform undo operation: ", response, " bad request");
      }
    } catch (error) {
      if (error.response) {
        console.error("Error response from server:", error.response);
      } else if (error.request) {
        console.error("Error with request:", error.request);
      } else {
        console.error("Error in setting up request:", error.message);
      }
    }
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
