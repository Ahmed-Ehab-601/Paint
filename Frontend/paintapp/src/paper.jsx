import React, { useEffect, useState } from "react";
import "./App.css";
import BrushIcon from './assets/brush.svg';
import PencilIcon from './assets/9165662_pencil_pen_icon.svg'; 
import CircleIcon from './assets/circle.svg';  
import rectIcon from './assets/rectangle-panoramic.svg'
import triangleIcon from './assets/triangle.svg'
import squareIcon from './assets/square.svg'
import EraserIcon from './assets/eraser.svg'
import lineicon from './assets/slash.svg'
import redoicon from './assets/redo-alt.svg'
import undoicon from './assets/undo-alt.svg'
import saveicon from './assets/folder-download.svg'
import uploadicon from'./assets/folder-upload.svg'
import texticon from './assets/t.svg'
import  ellipseicon from './assets/Ellipse2.svg';
import copyicon from './assets/copy.svg'
import menu from './assets/menu.svg';
import App from "./App";  // Import App component to pass shapeType

function Paper() { 
  const [shapeType, setShapeType] = useState(null);  // Store selected shape type
  const [color, setColor] = useState("#000000"); // Brush color state
  const [showMenu, setShowMenu] = useState(false);  // State for menu visibility
  const [borderWidth, setBorderWidth] = useState(8);   // Brush width state
  const [opacity, setOpacity] = useState(0.5);  // Brush opacity state
  const [borderColor, setBorderColor] = useState("#000000"); // Border color state
 const [filetosave,setfiletosave]=useState("");
 const [filetoload,setfiletoload]=useState("");

  const handleColorChange = (e) => {
    const selectedColor = e.target.value;
    setColor(selectedColor); // Set brush color
  };
  const handlefiletosave=(e)=>{
    setfiletosave(e.target.File[0]);
  }
  const handlefiletoload=(e)=>{
    setfiletoload(e.target.File[0]);
  }
  const handleBorderColorChange = (e) => {
    setBorderColor(e.target.value);  // Set border color independently
  };

  const toggleMenu = () => {
    setShowMenu(!showMenu);
  };
  useEffect(() => {
    
    if(shapeType!="")setShapeType("");
  }, [shapeType]);
  return (
    <div
      className="windowpaper"
      style={{
        top: "50%",
        left: "50%",
        position: "absolute",
        width: "100%",
        height: "100%",
        backgroundColor: "white",
        transform: "translate(-50%, -50%)",
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
        <button className="icon">
        <input
          id="filetosave"
          type="file"
          value={filetosave}
          onChange={handlefiletosave} 
          style={{
            opacity: 0,
            position: "absolute",
            cursor: "pointer",
            zIndex: "10",
          }}></input>
          <img src={saveicon} alt="save" />
        </button>
        <button className="icon">
        <input
          id="file"
          type="file"
          value={filetoload}
          onChange={handlefiletoload} 
          style={{
            opacity: 0,
            position: "absolute",
            cursor: "pointer",
            zIndex: "10",
          }}></input>
          <img src={uploadicon} alt="upload"  />
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
        <button className="icon">
          <img src={lineicon} alt="line"/>
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
<div >
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

      {/* Copy Button */}
     
 </div></div>
      {/* App Component */}
      <App type={shapeType} fill={color} stroke={borderColor} />
     
    </div>
  );
}

export default Paper;
