import { Routes, Route } from 'react-router-dom'

import './App.css'
// import AgregarMarca from './components/AgregarMarca'
import Header from './components/Header'
import Marcas from './pages/Marcas'
// import ListaDeMarcas from './components/ListaDeMarcas'
// // import Form from './components/Form'

function App() {
  return (
    <div className="flex flex-col items-center min-h-screen">
      <Header />
      <Routes>
        <Route path="/" element={<p>Home</p>} />
        <Route path="/marcas" element={<Marcas />} />
        <Route path="/productos" element={<p>Productos</p>} />
        <Route path="/categorias" element={<p>Categorias</p>} />
      </Routes>
      {/* <ListaDeMarcas />
      <AgregarMarca /> */}
    </div>
  )
}

export default App
