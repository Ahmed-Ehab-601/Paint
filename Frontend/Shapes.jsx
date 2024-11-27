import React, { useRef, useEffect } from "react";
import { Circle, Rect, Ellipse, Line, Transformer } from "react-konva";

const Shape = ({ shape, isSelected, onSelect, onChange }) => {
  //for resizing
  const shapeRef = useRef();
  const transformerRef = useRef();
  
  //for resizing
  useEffect(() => {
    if (isSelected) {
      transformerRef.current.nodes([shapeRef.current]);
      transformerRef.current.getLayer().batchDraw();
    }
  }, [isSelected]);

  const commonProps = {
    ref: shapeRef,
    x: shape.x,
    y: shape.y,
    draggable: true,
    fill: shape.fill,
    stroke: shape.stroke,
    strokeWidth: shape.strokeWidth,
    rotation: shape.rotation,
    onClick: onSelect,
    onTap: onSelect,
    onDragEnd: (e) => {
      onChange({
        ...shape,
        x: e.target.x(),
        y: e.target.y(),

        // update request
      });
    },
    //for resizing
    onTransformEnd: () => {
      const node = shapeRef.current;
      const scaleX = node.scaleX();
      const scaleY = node.scaleY();

      node.scaleX(1);
      node.scaleY(1);

      const newAttrs = {
        ...shape,
        x: node.x(),
        y: node.y(),
        rotation: node.rotation(),
      };

      if (shape.type === "circle") {
        newAttrs.radius = shape.radius * scaleX;
      } else if (shape.type === "ellipse") {
        newAttrs.radiusX = shape.radiusX * scaleX;
        newAttrs.radiusY = shape.radiusY * scaleY;
      } else if (shape.type === "rectangle" || shape.type === "square") {
        newAttrs.width = shape.width * scaleX;
        newAttrs.height = shape.height * scaleY;
      } else if (shape.type === "triangle" || shape.type === "line") {
        newAttrs.points = shape.points.map((val, index) =>
          index % 2 === 0 ? val * scaleX : val * scaleY
        );
      }

      onChange(newAttrs);
    },
  };

  switch (shape.type) {
    case "circle":
      return (
        <>
          <Circle {...commonProps} radius={shape.radius} />
          {isSelected && (
          //for resizing

            <Transformer
              ref={transformerRef}
              enabledAnchors={[
                "top-left",
                "top-right",
                "bottom-left",
                "bottom-right",
              ]}
              boundBoxFunc={(oldBox, newBox) => {
                const size = Math.max(newBox.width, newBox.height);
                return {
                  ...newBox,
                  width: size,
                  height: size,
                };
              }}
            />
          )}
        </>
      );

    case "rectangle":
      return (
        <>
          <Rect {...commonProps} width={shape.width} height={shape.height} />
          {isSelected && <Transformer ref={transformerRef}  />}
        </>
      );
    case "square":
      return (
        <>
          <Rect {...commonProps} width={shape.width} height={shape.width} />
          {isSelected && (
            <Transformer
              ref={transformerRef}
              enabledAnchors={[
                "top-left",
                "top-right",
                "bottom-left",
                "bottom-right",
              ]}
              boundBoxFunc={(oldBox, newBox) => {
                const size = Math.max(newBox.width, newBox.height);
                return {
                  ...newBox,
                  width: size,
                  height: size,
                };
              }}
            />
          )}
        </>
      );

    case "ellipse":
      return (
        <>
          <Ellipse
            {...commonProps}
            radiusX={shape.radiusX}
            radiusY={shape.radiusY}
          />
          {isSelected && <Transformer ref={transformerRef} />}
        </>
      );

    case "triangle":
    case "line":
      return (
        <>
          <Line
            {...commonProps}
            points={shape.points}
            closed={shape.type === "triangle"}
          />
          {isSelected && <Transformer ref={transformerRef} />}
        </>
      );

    default:
      console.warn(`Unsupported shape type: ${shape.type}`);
      return null;
  }
};

export default Shape;
