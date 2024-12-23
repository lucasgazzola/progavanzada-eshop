import LinkItem from './LinkItem'

function Header() {
  return (
    <header className="bg-[#1F2937] font-bold w-full flex items-center justify-between px-6 h-12">
      <nav>
        <ul className="flex gap-6">
          <li>
            <LinkItem to={'/'} text={'Home'} />
          </li>
          <li>
            <LinkItem to={'/marcas'} text={'Marcas'} />
          </li>
          <li>
            <LinkItem to={'/productos'} text={'Productos'} />
          </li>
        </ul>
      </nav>
      <div>
        <LinkItem to={'/login'} text={'Login'} />
      </div>
    </header>
  )
}

export default Header
