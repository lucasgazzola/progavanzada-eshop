const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white py-6 w-full">
      <div className="container mx-auto text-center">
        <p className="text-sm">
          &copy; {new Date().getFullYear()} MegaStore. Todos los derechos
          reservados.
        </p>
        <p className="text-sm">Desarrollado por Nosotros.</p>
      </div>
    </footer>
  )
}

export default Footer
