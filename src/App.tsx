import React from 'react';
import { Droplets, Download, Smartphone, Bell, RefreshCw } from 'lucide-react';

export default function App() {
  const downloadUrl = "https://example.com/moonwater.apk"; // Replace with your real URL

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white text-slate-800 flex flex-col items-center p-6 text-center" dir="rtl">
      <header className="py-12 flex flex-col items-center">
        <div className="w-20 h-20 bg-blue-500 rounded-2xl flex items-center justify-center shadow-lg mb-6">
          <Droplets className="text-white w-12 h-12" />
        </div>
        <h1 className="text-4xl font-extrabold text-blue-600 mb-2">MoonWater</h1>
        <p className="text-xl text-slate-600">אפליקציית תזכורת המים שלך</p>
      </header>

      <main className="max-w-md w-full space-y-8">
        <section className="bg-white p-8 rounded-3xl shadow-sm border border-slate-100 flex flex-col items-center">
          <h2 className="text-2xl font-bold mb-4">הורדת האפליקציה</h2>
          <p className="text-slate-500 mb-8 font-medium">
            MoonWater היא אפליקציית אנדרואיד המאפשרת לך לעקוב אחר צריכת המים היומית שלך בממשק חלק ואינטראקטיבי.
          </p>
          
          <a 
            href={downloadUrl}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-4 px-6 rounded-2xl font-bold flex items-center justify-center gap-2 transition-all transform active:scale-95 shadow-md shadow-blue-200"
          >
            <Download className="w-6 h-6" />
            הורד את קובץ ה-APK
          </a>
          <p className="text-xs text-slate-400 mt-4">
            * וודאו שיעד ה-URL הוחלף בכתובת האמיתית של ה-APK שלכם.
          </p>
        </section>

        <section className="grid grid-cols-2 gap-4">
          <FeatureCard 
            icon={<Smartphone className="text-blue-500" />} 
            title="מעקב אינטראקטיבי" 
            text="בקבוק מים אינטראקטיבי למעקב קל" 
          />
          <FeatureCard 
            icon={<Bell className="text-blue-500" />} 
            title="תזכורות חכמות" 
            text="קבל התראה כשמגיעה שעת השתייה" 
          />
          <FeatureCard 
            icon={<RefreshCw className="text-blue-500" />} 
            title="איפוס יומי" 
            text="התקבלות המים מתאפסת בכל בוקר" 
          />
          <FeatureCard 
            icon={<Droplets className="text-blue-500" />} 
            title="ללא בינה מלאכותית" 
            text="אפליקציה נקייה, מהירה ומקומית" 
          />
        </section>
      </main>

      <footer className="mt-auto py-8 text-slate-400 text-sm">
        &copy; {new Date().getFullYear()} MoonWater. כל הזכויות שמורות.
      </footer>
    </div>
  );
}

function FeatureCard({ icon, title, text }: { icon: React.ReactNode, title: string, text: string }) {
  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm border border-slate-50 flex flex-col items-center text-center">
      <div className="mb-2">{icon}</div>
      <h3 className="font-bold text-sm mb-1">{title}</h3>
      <p className="text-xs text-slate-400">{text}</p>
    </div>
  );
}
