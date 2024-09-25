import { Link } from 'react-router-dom'

function Header() {
  return (
    <header className="bg-red-600 w-full flex items-center justify-start h-10">
      <ul className="flex gap-6">
        <li>
          <Link to={'/'}>Home</Link>
        </li>
        <li>
          <Link to={'/marcas'}>Marcas</Link>
        </li>
        <li>
          <Link to={'/productos'}>Productos</Link>
        </li>
        <li>
          <Link to={'/categorias'}>Categorias</Link>
        </li>
      </ul>
    </header>
  )
}

export default Header
