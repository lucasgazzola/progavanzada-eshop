import { Link, useLocation } from 'react-router-dom'

function LinkItem({ to, text }: { to: string; text: string }) {
  const location = useLocation()
  return (
    <Link
      to={to}
      className={`text-white hover:text-[#60A5FA] ${
        location.pathname === to && 'border-b-2 border-blue-500'
      }`}>
      {text}
    </Link>
  )
}

export default LinkItem
