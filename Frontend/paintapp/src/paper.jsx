import React, { useEffect, useState } from "react";
import axios from "axios"; // Import axios for HTTP requests
import "./App.css";
import CircleIcon from "./assets/circle.svg";
import rectIcon from "./assets/rectangle-panoramic.svg";
import triangleIcon from "./assets/triangle.svg";
import squareIcon from "./assets/square.svg";
import EraserIcon from "./assets/eraser.svg";
import lineicon from "./assets/slash.svg";
import redoicon from "./assets/redo-alt.svg";
import undoicon from "./assets/undo-alt.svg";
import saveicon from "./assets/folder-download.svg";
import uploadicon from "./assets/folder-upload.svg";
import ellipseicon from "./assets/Ellipse2.svg";
import copyicon from "./assets/copy.svg";
import menu from "./assets/menu.svg";
import App from "./App"; // Import App component to pass shapeType

function Paper() {
  const [shapeType, setShapeType] = useState(null); // Store selected shape type
  const [color, setColor] = useState("#000000"); // Brush color state
  const [showMenu, setShowMenu] = useState(false); // State for menu visibility
  const [savemenu, setsaveMenu] = useState(false); // State for save menu visibility
  const [borderWidth, setBorderWidth] = useState(8); // Brush width state
  const [opacity, setOpacity] = useState(0.5); // Brush opacity state
  const [borderColor, setBorderColor] = useState("#000000"); // Border color state
  const [savepath, setsavepath] = useState(""); // Path for save
  const [filename, setFilename] = useState(""); // User-defined filename
  const [fileFormat, setfileFormat] = useState("json"); // File format (json/xml)
  const [filetoload, setfiletoload] = useState(""); // File to load
  const [directoryChosen, setDirectoryChosen] = useState(false); // State to track if directory is chosen
  const [selectedFilePath, setSelectedFilePath] = useState(""); // Store selected file path
  
  const handleColorChange = (e) => {
    setColor(e.target.value); // Set brush color
  };

  const handleSaveFile = async () => {
    if (!filename) {
      alert("Please enter a filename!");
      return;
    }

    // Construct file data object
    const data = {
      path: selectedFilePath, // Send the selected file path to the backend
      filename: filename,
      format: fileFormat,
    };
  
    try {
      const response = await axios.post("http://localhost:8080/shapes/save", data, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      console.log("File save response:", response.data);
      alert("File saved successfully: " + response.data.path);
    } catch (error) {
      console.error("Error saving file:", error.response?.data || error.message);
      alert("Error saving file.");
    }
  };

  const handleDirectoryChange = (e) => {
    const selectedFiles = e.target.files;
    if (selectedFiles.length > 0) {
      const directory = selectedFiles[0].webkitRelativePath.split('/')[0];
      setsavepath(directory);
      setSelectedFilePath(directory); // Save the directory path
      setDirectoryChosen(true); // Directory is now chosen
    }
  };

  const handlefiletoload = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setfiletoload(selectedFile);
      setsavepath(selectedFile.name); 
      setSelectedFilePath(selectedFile.webkitRelativePath); 
      sendFilePathToBackend(selectedFile.webkitRelativePath); 
    }
  };

  const sendFilePathToBackend = async (path) => {
    try {
      const response = await axios.post("http://localhost:8080/shapes/load", {
        filePath: path, // Send the selected file path to the backend
      });
      console.log("Backend response:", response.data);
      alert("File loaded successfully from path: " + path);
    } catch (error) {
      console.error("Error loading file:", error.response?.data || error.message);
      alert("Error loading file.");
    }
  };

  const handleBorderColorChange = (e) => {
    setBorderColor(e.target.value); // Set border color independently
  };

  const toggleMenu = () => {
    setShowMenu(!showMenu);
  };

  useEffect(() => {
    if (shapeType !== "") setShapeType("");
  }, [shapeType]);

  return (
    <div
      className="windowpaper"
      style={{
        top: "0",
        left: "0",
        position: "fixed", // Keep the window fixed
        width: "100vw", // Full viewport width
        height: "100vh", // Full viewport height
        backgroundColor: "white",
      }}
    >
      {/* Side Bar */}
      <div className="bar_onside">
        <button className="icon">
          <img src={undoicon} alt="undo" />
        </button>
        <button className="icon">
          <img src={redoicon} alt="redo" />
        </button>
       
        <button onClick={() => {
          if (!directoryChosen) {
            alert("Please select a directory to save first.");
            return;
          }
          setsaveMenu(true); // Show save menu only after directory is chosen
        }} className="icon">
          <img src={saveicon} alt="save" />
        </button>

        {/* Save Menu */}
        {savemenu && (
          <div className="save-menu">
            <div className="saveoption">
              <label>Choose the path and filename</label>
              <input
                id="file"
                type="file"
                onChange={handleDirectoryChange}
                webkitdirectory="true" // Explicitly set it as a string
                style={{
                  opacity: 0,
                  position: "absolute",
                  cursor: "pointer",
                  zIndex: "10",
                }}
              />
              <div>{savepath || "No path selected"}</div>
              <label>Enter filename:</label>
              <input
                type="text"
                value={filename}
                onChange={(e) => setFilename(e.target.value)}
                placeholder="Enter filename"
              />
            </div>

            <div className="saveoption">
              <label>Choose the format type</label>
              <select
                value={fileFormat}
                onChange={(e) => setfileFormat(e.target.value)}
              >
                <option value="json">JSON</option>
                <option value="xml">XML</option>
              </select>
            </div>

            <button onClick={handleSaveFile}>Save</button>
          </div>
        )}
        <button className="icon">
          <input
            id="file"
            type="file"
            onChange={handlefiletoload}
            style={{
              opacity: 0,
              position: "absolute",
              cursor: "pointer",
              zIndex: "10",
            }}
          />
          <img src={uploadicon} alt="upload" />
        </button>
        <button className="copybutton">
          <img src={copyicon} alt="copy" />
        </button>
      </div>

      {/* Bottom Bar */}
      <div className="bar_onbottom">
        <button className="icon">
          <img src={EraserIcon} alt="eraser" />
        </button>
        <button className="icon" onClick={() => setShapeType("line")}>
          <img src={lineicon} alt="line" />
        </button>
        {/* Shape Buttons */}
        <button className="icon" onClick={() => setShapeType("circle")}>
          <img src={CircleIcon} alt="circle" />
        </button>
        <button className="icon" onClick={() => setShapeType("triangle")}>
          <img src={triangleIcon} alt="triangle" />
        </button>
        <button className="icon" onClick={() => setShapeType("rectangle")}>
          <img src={rectIcon} alt="rectangle" />
        </button>
        <button className="icon" onClick={() => setShapeType("square")}>
          <img src={squareIcon} alt="square" />
        </button>
        <button className="icon" onClick={() => setShapeType("ellipse")}>
          <img src={ellipseicon} alt="ellipse" />
        </button>
      </div>

      <div className="baronleft">
        {/* Brush Color Button */}
        <button className="color" style={{ backgroundColor: color }}>
          <input
            id="brush"
            type="color"
            value={color}
            onChange={handleColorChange}
            style={{
              opacity: 0,
              position: "absolute",
              top: 0,
              left: 0,
              cursor: "pointer",
              zIndex: "2",
            }}
          />
        </button>

        {/* Border Color Button */}
        <button className="border" style={{ backgroundColor: borderColor }}>
          <input
            id="border"
            type="color"
            value={borderColor}
            onChange={handleBorderColorChange}
            style={{
              opacity: 0,
              position: "absolute",
              cursor: "pointer",
              zIndex: "10",
            }}
          />
        </button>

        {/* Menu Button */}
        <button className="icon" onClick={toggleMenu}>
          <img src={menu} alt="menuicon" />
        </button>

        {/* Menu Options */}
        {showMenu && (
          <div className="menu-options">
            <div className="option">
              <label htmlFor="border_width">Border Width:</label>
              <input
                type="range"
                id="border_width"
                min="1"
                max="17"
                value={borderWidth}
                onChange={(e) => setBorderWidth(e.target.value)}
              />
              <span>{borderWidth}</span>
            </div>
            <div className="option">
              <label htmlFor="opacity">Opacity:</label>
              <input
                type="range"
                id="opacity"
                min="1"
                max="100"
                value={opacity * 100}
                onChange={(e) => setOpacity(e.target.value / 100)}
              />
              <span>{(opacity * 100).toFixed(0)}%</span>
            </div>
          </div>
        )}
      </div>

      {/* App Component */}
      <App type={shapeType} fill={color} stroke={borderColor} />
    </div>
  );
}

export default Paper;
