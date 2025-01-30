import React, { useEffect, useState } from "react";
import axios from "axios"; // Import axios for HTTP requests
import { create } from "xmlbuilder2";
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
import { convert } from "xmlbuilder2";
function Paper() {
  const [shapes, setShapes] = useState([]);
  const [shapeType, setShapeType] = useState(null); // Store selected shape type
  const [color, setColor] = useState("#000000"); // Brush color state
  const [showMenu, setShowMenu] = useState(false); // State for menu visibility
  const [savemenu, setsaveMenu] = useState(false); // State for save menu visibility
  const [borderWidth, setBorderWidth] = useState(8); // Brush width state
  const [opacity, setOpacity] = useState(0.5); // Brush opacity state
  const [borderColor, setBorderColor] = useState("#000000"); // Border color state
  const [name, setname] = useState(""); // User-defined filename
  const [fileFormat, setfileFormat] = useState(""); // File format (json/xml)
  const [loadmenu,setloadmenu]=useState(false);
  const [loadfile,setloadfile]=useState("");
  const [selectedFilePath, setSelectedFilePath] = useState(""); // Store selected file path
  const [action,setAction]=useState("");
  const [loadedShapes,setloadedShapes]=useState([]);
  const [data,setData] = useState("");
  const handleColorChange = (e) => {
    setColor(e.target.value); // Set brush color
  };

  const handleSaveFile = async () => {
  
    try {

      const fileHandle = await window.showSaveFilePicker({
        types: [
          {
            description: "JSON File",
            accept: { "application/json": [".json"] },
          },
          {
            description: "XML File",
            accept: { "application/xml": [".xml"] },
          },
        ],
      });
  
    
      const fileExtension = fileHandle.name.split('.').pop().toLowerCase();
  
    let data;
      if (fileExtension === "json") {
        data =JSON.stringify(shapes);
      } else if (fileExtension === "xml") {
        const xml = create({ version: "1.0" })
        .ele("shapes");
    
    shapes.forEach((shape) => {
      if(shape.points){
        xml.ele("shape")
        .ele("type").txt(shape.type).up()
        .ele("x").txt(shape.x).up()
        .ele("y").txt(shape.y).up()
        .ele("opacity").txt(shape.opacity).up()
        .ele("id").txt(shape.id).up()
        .ele("width").txt(shape.width).up()
        .ele("height").txt(shape.height).up()
        .ele("radius").txt(shape.radius).up() 
        .ele("points").txt(`[${shape.points.join(",")}]`).up()
        .ele("fill").txt(shape.fill).up()
        .ele("stroke").txt(shape.stroke).up() 
        .ele("strokeWidth").txt(shape.strokeWidth).up()
        .ele("radiusX").txt(shape.radiusX).up()
        .ele("radiusY").txt(shape.radiusY).up()
        .ele("rotation").txt(shape.rotation).up() 
        ;
      }
      else{
        xml.ele("shape")
        .ele("type").txt(shape.type).up()
        .ele("x").txt(shape.x).up()
        .ele("y").txt(shape.y).up()
        .ele("opacity").txt(shape.opacity).up()
        .ele("id").txt(shape.id).up()
        .ele("width").txt(shape.width).up()
        .ele("height").txt(shape.height).up()
        .ele("radius").txt(shape.radius).up() 
        .ele("fill").txt(shape.fill).up()
        .ele("stroke").txt(shape.stroke).up() 
        .ele("strokeWidth").txt(shape.strokeWidth).up()
        .ele("radiusX").txt(shape.radiusX).up()
        .ele("radiusY").txt(shape.radiusY).up()
        .ele("rotation").txt(shape.rotation).up() 
        ;
      }
   
  });

      data = xml.end({ prettyPrint: true });
         console.log(data)
      } else {
        throw new Error("Unsupported file type");
      }
  
      
      const writable = await fileHandle.createWritable();
      await writable.write(data);
      await writable.close();
  
      console.log("File saved successfully!");
    } catch (error) {
      console.error("Error saving file:", error);
    }
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file.name.endsWith(".json")) {
      setfileFormat("json")
    } else if (file.name.endsWith(".xml")) {
      setfileFormat("xml")
    }
    setloadfile(file)
    console.log(file)
    
};
   
  
  const sendFile = async () => {
    if(fileFormat === "json"){
      console.log(loadfile);
      const reader = new FileReader();
      reader.onload = (event) => {
         const data = JSON.parse(event.target.result);
          console.log(JSON.stringify(data));
          setData(data)
          setloadedShapes(data)   
      };
      reader.readAsText(loadfile);
    }
    else if(fileFormat === "xml"){
      const reader = new FileReader();
      reader.onload = (event) => {
        const xmlDoc = convert(event.target.result, { format: "object" });
        const shapesData = xmlDoc.shapes.shape;
        const data = Array.isArray(shapesData) ? shapesData : [shapesData];
        const processedShapes = data.map(shape => {
          const numericAttributes = ["x", "y", "opacity", "id", "width", "height", "radius", "strokeWidth", "radiusX", "radiusY", "rotation"];
          numericAttributes.forEach(attr => {
            if (shape[attr]) {
              shape[attr] = parseFloat(shape[attr]);
            }
          });
          if (shape.points && shape.points !== "null") {
            shape.points = JSON.parse(shape.points);  
          }
        
          return shape;
        });
        console.log(JSON.stringify(processedShapes))
        setloadedShapes(processedShapes)
      };
      reader.readAsText(loadfile);
    }else{
      console.log("choose file to load!!!")
      return
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
        <button onClick={()=>{setsaveMenu(!savemenu);setloadmenu(false);handleSaveFile()}} className="icon" onMouseEnter={() => setname("save")}
          onMouseLeave={() => setname("")}>
          <img src={saveicon} alt="save" />
        </button>
        {/* Save Menu */}
       
        {false && (
          <div className="save-menu">
            <div className="saveoption">
              <div>
              <label style={{display:"block", color:"black"}}>Enter the path and filename</label>
              <input type="filepath"
              value={selectedFilePath}
              onChange={(e)=> setSelectedFilePath(e.target.value)}
              />
              </div>
              <div style={{display:"felx", justifyContent:"space-between"} } >
              <label style={{marginRight:"10px",display:"block", color:"black"}}>Choose the format type</label>
              <select 
                value={fileFormat}
                onChange={(e) => setfileFormat(e.target.value)}
              >
                <option style={{color:"black"}} value="json">JSON</option>
                <option value="xml">XML</option>
              </select>
              </div>
    
            </div>
          
            <button  id="save" onClick={handleSaveFile}>Save</button>
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
                <input
                    type="file"
                    onChange={handleFileUpload}
                    //accept=".json,.xml"
                 />
      
                </div>
              <button id="save" onClick={() => sendFile()}>Load</button>
            </div>
               )}
{/*copy button */}
           <button className="icon" onClick={()=>setAction("copy")}onMouseEnter={() => setname("copy")}
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
                onChange={(e) => {setBorderWidth(parseInt(e.target.value))}}
                
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

<App type={shapeType} fill={color} stroke={borderColor} action={action} loadedShapes={loadedShapes} opacity={opacity} strokeWidth={borderWidth} shapes={shapes} setShapes={setShapes}/>
    </div>
  );
}

export default Paper;
