import { Routes, Route } from 'react-router-dom'

import Header from './components/Header'
import Marcas from './pages/Marcas'
import Productos from './pages/Productos'

import './App.css'
import Home from './pages/Home'

function App() {
  return (
    <div className="flex flex-col items-center min-h-screen">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/marcas" element={<Marcas />} />
        <Route path="/productos" element={<Productos />} />
        {/* <Route path="/categorias" element={<p>Categorias</p>} /> */}
      </Routes>
      {/* <ListaDeMarcas />
      <AgregarMarca /> */}
    </div>
  )
}

export default App
