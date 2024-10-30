/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,,html,jsp}",
    "./index.html"
  ],
  theme: {
    extend: {
      fontFamily: {
        'DynaPuff': ['DynaPuff', 'sans-serif'],
      },
    },
  },
  plugins: [],
}

