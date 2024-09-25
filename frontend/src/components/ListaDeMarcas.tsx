import { useEffect, useState } from 'react'

function ListaDeMarcas() {
  const [listaDeMarcas, setListaDeMarcas] = useState<
    Array<{
      id: number
      nombre: string
      descripcion: string
    }>
  >([])

  useEffect(() => {
    const fetchMarcas = async () => {
      const response = await fetch('http://localhost:8080/api/marcas/')
      const data = await response.json()
      console.log(data)
      setListaDeMarcas(data)
    }
    fetchMarcas()
  }, [])

  return (
    <ul className="bg-yellow-100 text-slate-950">
      {listaDeMarcas.map(marca => (
        <li key={marca.id}>
          <div className="flex flex-col">
            <p> Nombre: {marca.nombre}</p>
            <p>Descripcion: {marca.descripcion}</p>
          </div>
        </li>
      ))}
    </ul>
  )
}

export default ListaDeMarcas
