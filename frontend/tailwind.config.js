/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  darkMode: 'class',
  theme: {
    extend: {
      fontFamily: {
        helvetica: ['Helvetica', 'sans-serif'],
        helveticaOblique: ['Helvetica Oblique', 'sans-serif'],
        helveticaBoldOblique: ['Helvetica Bold Oblique', 'sans-serif'],
        helveticaBold: ['Helvetica Bold', 'sans-serif'],
        helveticaCompressed: ['Helvetica Compressed', 'sans-serif'],
        helveticaRoundedBold: ['Helvetica Rounded Bold', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
