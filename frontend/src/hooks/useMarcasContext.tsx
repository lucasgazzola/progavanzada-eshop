import { useContext } from 'react'
import { MarcasContext } from '../context/MarcasContext'

export default function useMarcasContext() {
  const context = useContext(MarcasContext)
  if (!context) {
    throw new Error('useMarcas must be used within its ContextProvider')
  }
  return context
}
