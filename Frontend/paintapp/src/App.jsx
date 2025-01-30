import React, { useState, useEffect } from "react";
import { Stage, Layer, Line } from "react-konva"; // Added Line for preview rendering
import axios from "axios"; // Import axios for HTTP requests
import Shape from "./shapes"; // Define Shape component separately

const baseURL = "http://localhost:8080/shape"

const App = ({ type, stroke, fill, action ,loadedShapes, opacity,strokeWidth,shapes,setShapes}) => {

  const [selectedId, setSelectedId] = useState(null);
  const [shapeToAdd, setShapeToAdd] = useState(null);

  const [linePoints, setLinePoints] = useState([]);
  const [mousePosition, setMousePosition] = useState(null);

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
      case "deleteAll":
        deleteAll();
        break;
    }
  },[action])

  useEffect(()=> {
    deleteAll();
  },[]);

  useEffect(() => {
    // console.log(strokeWidth)
    if(selectedId)
      updateShape(selectedId, {stroke});
  },[ stroke])
  
  const [sw, setsw] = useState(3);
  const [op, setop] = useState(1);
  
  useEffect(() => {
    setsw(strokeWidth)
    console.log(strokeWidth)
    if(selectedId)
      updateShape(selectedId, {strokeWidth});
  },[ strokeWidth])

  useEffect(() => {
    console.log( opacity ,"opacity change")
    setop(opacity);
    if(selectedId)
      updateShape(selectedId, {opacity});
  },[opacity])

  useEffect(() => {
    
    if(selectedId)
      updateShape(selectedId, {fill});
  },[fill])

  useEffect(()=> {
    setShapes(loadedShapes)
  },[loadedShapes]);

  useEffect(() => {
    if (type !== "") {
      setShapeToAdd(type);
    }
  }, [type]);

  useEffect(() => {
    setLinePoints([]);
    setMousePosition(null);
  }, [shapeToAdd]);
  
  useEffect(() => {
    if(linePoints.length === 4)
    {
      addShape("line", 0, 0, fill, stroke, linePoints, opacity, strokeWidth);
      setMousePosition(null); // Reset mouse position if needed
      setShapeToAdd(null); // Reset shape to add
      setLinePoints([]); // Reset points for the next line
    }
  }, [linePoints])

  // Create shape function with API call
  
  

  const handleMouseMove = (e) => {
    const stage = e.target.getStage();
    const pointerPosition = stage.getPointerPosition();
    if (!pointerPosition) return;

    setMousePosition({ x: pointerPosition.x, y: pointerPosition.y });
  };

  const handleCanvasClick = async (e) => {
    const stage = e.target.getStage();
    
    // Get the mouse down position
    const pointerPosition = stage.getPointerPosition();
    if (!pointerPosition) return;
    const { x, y } = pointerPosition;
    
    
    console.log(shapeToAdd);
    console.log(x, y);

  
    // Add the line on mouse click
    if (shapeToAdd === "line") {
      setLinePoints([...linePoints, x, y]);
      console.log(linePoints);
      return;
    }
  // Deselect the shape (send the update that happened to it and deselect it)
  if (e.target === stage && selectedId !== null) {
    setSelectedId(null);
  }
    // For other shapes
    if (shapeToAdd && shapeToAdd!== "line") {
      addShape(shapeToAdd, x, y, fill, stroke, opacity, strokeWidth);
      setShapeToAdd(null);
    }
  };

  const handleShapeSelect = (shapeId) => {
    setSelectedId(shapeId);
    console.log("selectedId: ", selectedId);

    //move the selected shape to the top layer
    setShapes((prevShapes) => {
      const selectedShape = prevShapes.find((shape) => shape.id === shapeId);
      if (!selectedShape) return prevShapes;
      const updatedShapes = prevShapes.filter((shape) => shape.id !== shapeId);
      return [...updatedShapes, selectedShape];
    });
  };



  const addShape = async (type, x, y, fill, stroke, points, opacity, strokeWidth) => {
    console.log(strokeWidth)
    const shapeDetails = {
      type,
      x,
      y,
      fill,
      stroke,
      points: type === "line" ? points : null,
      opacity:op,
      strokeWidth:sw
    };
    console.log(shapeDetails);
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
    console.log(updatedShape)
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
        const response = await axios.delete(`http://localhost:8080/shape/${selectedId}`);
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
        const response = await axios.put(`http://localhost:8080/shape/copy/${selectedId}`);
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

  const deleteAll = async () => {
    setShapes([]);
    try {
      const response = await axios.delete(`http://localhost:8080/shape/deleteAll`);
      if (response.status === 200) {
        console.log("delete all done")
      } else {
        console.error("Failed to delete all shape: ", response, " bad request");
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

  const undo = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/shape/undo`)
      if (response.status === 200) {
        const { type, shapeDto } = response.data;
  
        switch (type) {
          case "edit":
            {
              // Update the shape with the new data from shapeDTO
              const updatedShapes = shapes.map((shape) =>
                shape.id === shapeDto.id ? shapeDto : shape
              );
              setShapes(updatedShapes); // Update the state with the modified array
            }
            break;
  
          case "create":
            {
              // Add the new shape to the array with its new id
              setShapes((prevShapes) => [...prevShapes, shapeDto]);
            }
            break;
  
          case "delete":
            {
              // Remove the shape using the given shapeDTO id
              const updatedShapes = shapes.filter((shape) => shape.id !== shapeDto.id);
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
      const response = await axios.put(`http://localhost:8080/shape/redo`);
      if (response.status === 200) {
        const { type, shapeDto } = response.data;
  
        switch (type) {
          case "edit":
            {
              // Update the shape with the new data from shapeDTO
              const updatedShapes = shapes.map((shape) =>
                shape.id === shapeDto.id ? shapeDto : shape
              );
              setShapes(updatedShapes); // Update the state with the modified array
            }
            break;
  
          case "create":
            {
              // Add the new shape to the array with its new id
              setShapes((prevShapes) => [...prevShapes, shapeDto]);
            }
            break;
  
          case "delete":
            {
              // Remove the shape using the given shapeDTO id
              const updatedShapes = shapes.filter((shape) => shape.id !== shapeDto.id);
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
                onChange={(newAttrs) => {updateShape(shape.id, newAttrs)}}
              />
            ))}

            {shapeToAdd === "line" && linePoints.length === 2 && mousePosition && (
              <Line
                points={[...linePoints, mousePosition.x, mousePosition.y]}
                stroke={stroke}
                strokeWidth={strokeWidth}
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
