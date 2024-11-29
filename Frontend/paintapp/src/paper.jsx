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
import trash from './assets/trash.svg'
function Paper() {
  const [shapeType, setShapeType] = useState(null); // Store selected shape type
  const [color, setColor] = useState("#000000"); // Brush color state
  const [showMenu, setShowMenu] = useState(false); // State for menu visibility
  const [savemenu, setsaveMenu] = useState(false); // State for save menu visibility
  const [borderWidth, setBorderWidth] = useState(8); // Brush width state
  const [opacity, setOpacity] = useState(0.5); // Brush opacity state
  const [borderColor, setBorderColor] = useState("#000000"); // Border color state
  const [savepath, setsavepath] = useState("No path selected"); // Path for save
  const [filename, setFilename] = useState(""); // User-defined filename
  const [fileFormat, setfileFormat] = useState("json"); // File format (json/xml)
  const [loadmenu,setloadmenu]=useState(false);
  const [loadfile,setloadfile]=useState("");
  const [directoryChosen, setDirectoryChosen] = useState(false); // State to track if directory is chosen
  const [selectedFilePath, setSelectedFilePath] = useState(""); // Store selected file path
  const [action,setAction]=useState("");
  const [loadedShapes,setloadedShapes]=useState([]);
  const handleColorChange = (e) => {
    setColor(e.target.value); // Set brush color
  };

  const handleSaveFile = async () => {
  ///  setSelectedFilePath(");
    if (!filename) {
      alert("Please enter a filename!");
      return;
    }
    console.log({selectedFilePath});

    if (!selectedFilePath) {
      alert("Please choose a save directory first!");
      return;
    }
  
    // Construct file data object
    const data =
      {
        type : fileFormat,
        path : selectedFilePath //+ "//"+filename+"."+fileFormat
      }
    ;
    console.log(data);
  
    try {
      const response = await axios.put("http://localhost:8080/shape/save", data, {
        headers: {
          "Content-Type": "application/json",
        },
      });
  
      if (response.status == 200) {
        alert("File saved successfully: " + response.data.path);
      } else {
        alert("Save successful but no path returned.");
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || "An error occurred while saving the file.";
      console.error("Error saving file:", errorMessage);
      alert(errorMessage);
    }
  };
  
  const handleDirectoryChange = async () => {
    try {
      // Use the Directory Picker API to let the user select a directory
      const directoryHandle = await window.showDirectoryPicker();
      const directoryPath = directoryHandle.name; // Get the directory name
      setsavepath(directoryPath); // Display directory in UI
      setSelectedFilePath(directoryPath); // Save for backend
      setDirectoryChosen(true); // Set flag to indicate directory was selected
      setsaveMenu(true); // Open save menu once the directory is selected
    } catch (error) {
      alert("No directory selected or error occurred.");
    }
  };
  

  
  const handlefiletoload = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      const fullPath = selectedFile.webkitRelativePath || selectedFile.name; // Use full path if available
      setsavepath(fullPath); // Display or save the file path in the UI
      setSelectedFilePath(fullPath); // Store the full file path
      sendFile(fullPath); // Send the file path to the backend for loading
    }
  };
  
  const sendFile = async (path) => {
    try {
      const x = path.split(".")[1];
      console.log(path)
      console.log(x)
      const response = await axios.put("http://localhost:8080/shape/load", {
        path : path, // Send the full file path to the backend
        type : x
      });
  
      if (response.status == 200) {
        console.log(response.data)
        setloadedShapes(response.data); // Update the shapes state with the response array
        alert("File loaded successfully from path: " + path);
      } else {
        alert("Unexpected response format from backend.");
      }
    } catch (error) {
      const errorMessage = error.response?.data?.message || "An error occurred while loading the file.";
      console.error("Error loading file:", errorMessage);
      alert(errorMessage);
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

  useEffect(() => {
    if (action !== "") setAction("");
  }, [action]);

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
        <button className="icon" onClick={()=>setAction("undo")}>
          <img src={undoicon} alt="undo" />
        </button>
        <button className="icon" onClick={()=>setAction("redo")}>
          <img src={redoicon} alt="redo" />
        </button>
       
        <button onClick={()=>setsaveMenu(!savemenu)} className="icon">
          <img src={saveicon} alt="save" />
        </button>
        {/* Save Menu */}
        {savemenu && (
          <div className="save-menu">
            <div className="saveoption">
              <label>Choose the path and filename</label>
              <input type="filepath"
              value={selectedFilePath}
              onChange={(e)=> setSelectedFilePath(e.target.value)}
              />
              {/* <label>Enter filename:</label>
              <input
                type="text"
                value={filename}
                onChange={(e) => setFilename(e.target.value)}
                placeholder="Enter filename"
              /> */}
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
<button className="icon" onClick={() => setloadmenu(!loadmenu)}>
  <img src={uploadicon} alt="upload" />
</button>
{loadmenu && (
  <div className="save-menu">
    <div className="saveoption">
      <label>Enter or select the file path to load:</label>
      <input
        type="text"
        value={loadfile}
        onChange={(e) => setloadfile(e.target.value)}
        placeholder="Enter file path"
      />
      
    </div>
    <button onClick={() => sendFile(loadfile)}>Load</button>
  </div>
)}

        <button className="copybutton" onClick={()=>setAction("copy")}>
          <img src={copyicon} alt="copy" />
        </button>
      </div>

      {/* Bottom Bar */}
      <div className="bar_onbottom">
        <button className="icon" onClick={()=> setAction("delete")}>
          <img src={EraserIcon} alt="eraser"  />
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
        <button className="icon" onClick={()=>{setAction("deleteAll")}}>
          <img src={trash} ></img>
        </button>

      </div>


      <App type={shapeType} fill={color} stroke={borderColor} action={action} loadedShapes={loadedShapes}/>

    </div>
  );
}

export default Paper;
