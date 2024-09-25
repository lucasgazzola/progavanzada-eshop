type Props = {
  field: 'nombre' | 'descripcion'
  type?: string
  nuevaMarca: {
    nombre: string
    descripcion: string
  }
  placeholder?: string
  handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}

function FormGroup({
  field,
  type,
  handleInputChange,
  nuevaMarca,
  placeholder,
}: Props) {
  const upperCaseField = field.charAt(0).toUpperCase() + field.slice(1)

  return (
    <label className="flex flex-col" htmlFor={field}>
      {upperCaseField}:{' '}
      <input
        className="w-full px-2 py-1 outline-none rounded-md"
        onChange={handleInputChange}
        placeholder={placeholder || upperCaseField}
        value={nuevaMarca[field]}
        name={field}
        type={type || 'text'}
      />
    </label>
  )
}

export default FormGroup
