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
  const [name, setname] = useState(""); // User-defined filename
  const [fileFormat, setfileFormat] = useState("json"); // File format (json/xml)
  const [loadmenu,setloadmenu]=useState(false);
  const [loadfile,setloadfile]=useState("");
  const [selectedFilePath, setSelectedFilePath] = useState(""); // Store selected file path
  const [action,setAction]=useState("");
  const [loadedShapes,setloadedShapes]=useState([]);
  const handleColorChange = (e) => {
    setColor(e.target.value); // Set brush color
  };

  const handleSaveFile = async () => {
  
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
    setBorderColor(e.target.value);
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
        position: "fixed",
        width: "100vw", 
        height: "100vh", 
        backgroundColor: "white",
      }}
    >
      <div className="icon-name">{name}</div>
      {/* Side Bar */}
      <div className="bar_onside">
{/*undo button */}
        <button className="icon" onClick={()=>setAction("undo")} onMouseEnter={() => setname("undo")}
        onMouseLeave={() => setname("")}>
          <img src={undoicon} alt="undo" />
        </button>
 {/*redo button */}
        <button className="icon" onClick={()=>setAction("redo")}onMouseEnter={() => setname("redo")}
        onMouseLeave={() => setname("")}>
          <img src={redoicon} alt="redo" />
        </button>
 {/*save button */}
        <button onClick={()=>{setsaveMenu(!savemenu);setloadmenu(false);}} className="icon" onMouseEnter={() => setname("save")}
          onMouseLeave={() => setname("")}>
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
       { /*load button  */}
        <button className="icon" onClick={() => {setloadmenu(!loadmenu);setsaveMenu(false);}} onMouseEnter={() => setname("Load")}
          onMouseLeave={() => setname("")}>
           <img src={uploadicon} alt="upload" />
        </button>
             { loadmenu && (
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
{/*copy button */}
           <button className="copybutton" onClick={()=>setAction("copy")}onMouseEnter={() => setname("copy")}
             onMouseLeave={() => setname("")}>
              <img src={copyicon} alt="copy" />
            </button>
      </div>

    {/* Bottom Bar */}
      <div className="bar_onbottom">
      {/*delete button */}
        <button className="icon" onClick={()=> setAction("delete")}onMouseEnter={() => setname("delete")}
              onMouseLeave={() => setname("")}>
              <img src={EraserIcon} alt="eraser"  />
        </button>
        {/*line button */}
      
        <button className="icon" onClick={() => setShapeType("line")}onMouseEnter={() => setname("line")}
           onMouseLeave={() => setname("")}>
            <img src={lineicon} alt="line" />
        </button>
        {/* Shape Buttons */}
        <button className="icon" onClick={() => setShapeType("circle")}onMouseEnter={() => setname("circle")}
              onMouseLeave={() => setname("")}>
               <img src={CircleIcon} alt="circle" />
       </button>
        <button className="icon" onClick={() => setShapeType("triangle")} onMouseEnter={() => setname("triangle")}
              onMouseLeave={() => setname("")}>
           <img src={triangleIcon} alt="triangle" />
         </button>
         <button className="icon" onClick={() => setShapeType("rectangle")}onMouseEnter={() => setname("rectangle")}
             onMouseLeave={() => setname("")}>
          <img src={rectIcon} alt="rectangle" />
        </button>
        <button className="icon" onClick={() => setShapeType("square")}onMouseEnter={() => setname("square")}
            onMouseLeave={() => setname("")}>
          <img src={squareIcon} alt="square" />
        </button>
        <button className="icon" onClick={() => setShapeType("ellipse")}onMouseEnter={() => setname("Ellipse")}
             onMouseLeave={() => setname("")}>
          <img src={ellipseicon} alt="ellipse" />
        </button>
      </div>

      <div className="baronleft">
        {/* Brush Color Button */}
        <button className="color" style={{ backgroundColor: color }} onMouseEnter={() => setname("shape color")}
        onMouseLeave={() => setname("")}>
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
        <button className="border" style={{ backgroundColor: borderColor }}onMouseEnter={() => setname("border color")}
            onMouseLeave={() => setname("")}>
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
                max="25"
                value={borderWidth}
                onChange={(e) => setBorderWidth(parseInt(e.target.value))}
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

{/*delete all button */}
        <button className="icon" onClick={()=>{setAction("deleteAll")}}
         onMouseEnter={() => setname("delete all")} onMouseLeave={() => setname("")}>
          <img src={trash} ></img>
        </button>
      </div>

<App type={shapeType} fill={color} stroke={borderColor} action={action} loadedShapes={loadedShapes} opacity={opacity} strokeWidth={borderWidth}/>
    </div>
  );
}

export default Paper;
