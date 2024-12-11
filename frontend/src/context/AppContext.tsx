import React, { createContext, useState, useEffect } from 'react'
import Cookies from 'js-cookie'
import ToastType from '../enums/ToastType'
import Toast from '../components/Toast'
import { useNavigate } from 'react-router-dom'

type AppContextType = {
  showToast: (toastMessage: ToastMessage) => void
  isModalOpen: boolean
  setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>
  isAuthenticated: boolean
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>
}

type ToastMessage = {
  message: string
  type: ToastType
}

export const AppContext = createContext<AppContextType>({
  showToast: () => {},
  isModalOpen: false,
  setIsModalOpen: () => {},
  isAuthenticated: false,
  setIsAuthenticated: () => {},
})

export const AppContextProvider = ({ children }: React.PropsWithChildren) => {
  const [toast, setToast] = useState<ToastMessage | null>(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const navigate = useNavigate()

  // Function to validate the token
  const validateToken = async () => {
    try {
      const response = await fetch(
        'http://localhost:8080/api/auth/validate-token',
        {
          method: 'POST',
          credentials: 'include',
        }
      )
      console.log({ response })

      if (response.ok) {
        setIsAuthenticated(true)
        navigate('/')
      } else {
        setIsAuthenticated(false)
        navigate('/login')
      }
    } catch (error) {
      console.error('Error validating token:', error)
      setIsAuthenticated(false)
      navigate('/login')
    }
  }

  useEffect(() => {
    validateToken()
  }, [])

  useEffect(() => {
    console.log({ isAuthenticated })
  }, [isAuthenticated])

  return (
    <AppContext.Provider
      value={{
        showToast: toastMessage => {
          setToast(toastMessage)
        },
        isModalOpen,
        setIsModalOpen,
        isAuthenticated,
        setIsAuthenticated,
      }}>
      {toast && (
        <Toast
          message={toast.message}
          type={toast.type}
          onClose={() => {
            setToast(null)
          }}
        />
      )}
      {children}
    </AppContext.Provider>
  )
}
