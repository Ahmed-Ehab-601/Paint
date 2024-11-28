import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Paper from './paper.jsx'
createRoot(document.getElementById('root')).render(
  <StrictMode>
   
    <Paper/>
  </StrictMode>,
)
