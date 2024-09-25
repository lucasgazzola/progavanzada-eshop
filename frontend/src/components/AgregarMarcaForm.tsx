import { useState } from 'react'
import FormGroup from './FormGroup'

function AgregarMarcaForm() {
  const [nuevaMarca, setNuevaMarca] = useState({
    nombre: '',
    descripcion: '',
  })

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNuevaMarca({
      ...nuevaMarca,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    const response = await fetch('http://localhost:8080/api/marcas/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ ...nuevaMarca }),
    })
    const data = await response.json()

    console.log(data)
  }

  return (
    <form
      className="flex flex-col bg-amber-700 p-10 rounded-lg gap-6"
      onSubmit={handleSubmit}>
      {/* <label htmlFor="nombre">
        Nombre:{' '}
        <input
          onChange={handleInputChange}
          value={nuevaMarca.nombre}
          name="nombre"
          type="text"
        />
      </label>
      <label htmlFor="descripcion">
        Descripcion:{' '}
        <input
          onChange={handleInputChange}
          value={nuevaMarca.descripcion}
          name="descripcion"
          type="descripcion"
        />
      </label> */}
      <FormGroup
        nuevaMarca={nuevaMarca}
        field="nombre"
        placeholder="Ingrese el nombre de la marca"
        handleInputChange={handleInputChange}
      />
      <FormGroup
        nuevaMarca={nuevaMarca}
        field="descripcion"
        placeholder="Ingrese la descripcion"
        handleInputChange={handleInputChange}
      />
      <button className="bg-cyan-400 rounded-md text-black py-1" type="submit">
        Submit
      </button>
    </form>
  )
}

export default AgregarMarcaForm
