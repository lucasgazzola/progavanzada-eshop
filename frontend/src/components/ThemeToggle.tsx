import { useEffect, useState } from 'react'

function ThemeToggle() {
  const [theme, setTheme] = useState(localStorage.getItem('theme') || 'system')

  useEffect(() => {
    const localTheme = localStorage.getItem('theme')
    if (localTheme) {
      setTheme(localTheme)
    }
  }, [])

  useEffect(() => {
    console.log(theme)
    const root = document.documentElement
    if (
      theme === 'dark' ||
      (theme === 'system' &&
        window.matchMedia('(prefers-color-scheme: dark)').matches)
    ) {
      root.classList.add('dark')
    } else {
      root.classList.remove('dark')
    }
    localStorage.setItem('theme', theme)
  }, [theme])

  return (
    <button
      onClick={() => {
        setTheme(prev => {
          if (prev === 'light') return 'dark'
          return 'light'
        })
      }}
      className="p-2 bg-gray-200 dark:bg-gray-800 text-gray-800 dark:text-gray-200 rounded-md">
      {theme === 'light' && '🌞 Light'}
      {theme === 'dark' && '🌜 Dark'}
      {theme === 'system' && '💻 System'}
    </button>
  )
}

export default ThemeToggle
