import { Routes, Route } from 'react-router-dom'

import Header from './components/Header'
import Marcas from './pages/Marcas'
import Productos from './pages/Productos'

import './App.css'
import Home from './pages/Home'
import Footer from './components/Footer'
import NotFoundPage from './pages/NotFoundPage'
import Login from './pages/Login'
import { MarcasProvider } from './context/MarcasContext'
import Register from './pages/Register'

function App() {
  return (
    <div className="flex flex-col bg-[#dedede] dark:bg-[#151515] items-center min-h-screen w-full">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route
          path="/marcas"
          element={
            <MarcasProvider>
              <Marcas />
            </MarcasProvider>
          }
        />
        <Route path="/productos" element={<Productos />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="*" element={<NotFoundPage />} />
        {/* <Route path="/categorias" element={<p>Categorias</p>} /> */}
      </Routes>
      {/* <ListaDeMarcas />
        <AgregarMarca /> */}
      <Footer />
    </div>
  )
}

export default App
