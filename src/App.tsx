import React from 'react';
import { motion } from 'motion/react';
import { Droplets, Download, Smartphone, Bell, RefreshCw, ChevronLeft, ShieldCheck, Heart } from 'lucide-react';

export default function App() {
  const downloadUrl = "/moonwater.apk";

  return (
    <div className="min-h-screen bg-[#F8FAFC] text-slate-900 selection:bg-blue-100 selection:text-blue-700" dir="rtl">
      {/* Navigation */}
      <nav className="max-w-7xl mx-auto px-6 py-8 flex justify-between items-center">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 bg-blue-600 rounded-xl flex items-center justify-center shadow-md shadow-blue-200">
            <Droplets className="text-white w-6 h-6" />
          </div>
          <span className="text-2xl font-bold tracking-tight text-slate-800">MoonWater</span>
        </div>
        <div className="hidden md:flex items-center gap-8 text-sm font-medium text-slate-500">
          <a href="#features" className="hover:text-blue-600 transition-colors">תכונות</a>
          <a href="#download" className="hover:text-blue-600 transition-colors">הורדה</a>
          <a href="#about" className="hover:text-blue-600 transition-colors">אודות</a>
        </div>
        <a 
          href={downloadUrl}
          className="bg-slate-900 text-white px-5 py-2.5 rounded-full text-sm font-bold hover:bg-slate-800 transition-all active:scale-95 shadow-lg shadow-slate-200"
        >
          הורד עכשיו
        </a>
      </nav>

      <main>
        {/* Hero Section */}
        <section className="max-w-7xl mx-auto px-6 pt-20 pb-32 grid md:grid-cols-2 gap-16 items-center">
          <motion.div 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
          >
            <div className="inline-flex items-center gap-2 px-3 py-1 bg-blue-50 text-blue-600 rounded-full text-xs font-bold mb-6 tracking-wide uppercase">
              <ShieldCheck className="w-3.5 h-3.5" />
              פרטיות מובטחת - ללא מעקב
            </div>
            <h1 className="text-6xl md:text-7xl font-extrabold text-slate-900 leading-[1.1] mb-8 tracking-tighter">
              הדרך הפשוטה <br/>
              <span className="text-blue-600 italic">להישאר רוויים.</span>
            </h1>
            <p className="text-xl text-slate-500 mb-10 leading-relaxed max-w-lg">
              שמור על הבריאות שלך עם MoonWater. תזכורות חכמות, מעקב אינטראקטיבי ועיצוב נקי שעוזר לך להשיג את יעד שתיית המים היומי שלך - בעברית מלאה.
            </p>
            <div className="flex flex-col sm:flex-row gap-4">
              <a 
                href={downloadUrl}
                download="MoonWater.apk"
                className="inline-flex items-center justify-center gap-3 bg-blue-600 text-white px-10 py-5 rounded-2xl font-bold text-lg hover:bg-blue-700 transition-all shadow-xl shadow-blue-100 group"
              >
                <Download className="w-6 h-6 group-hover:scale-110 transition-transform" />
                הורד את האפליקציה
              </a>
              <div className="flex items-center gap-4 px-6 py-4 bg-white border border-slate-100 rounded-2xl shadow-sm">
                <Smartphone className="text-slate-400 w-6 h-6" />
                <div className="text-right">
                  <p className="text-[10px] text-slate-400 font-bold uppercase tracking-widest leading-none mb-1">פלטפורמה</p>
                  <p className="text-sm font-bold text-slate-700 leading-none">Android APK</p>
                </div>
              </div>
            </div>
          </motion.div>
          <motion.div 
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.8, delay: 0.2 }}
            className="relative"
          >
            <div className="absolute inset-0 bg-blue-200 blur-3xl opacity-20 rounded-full"></div>
            <div className="relative bg-white p-4 rounded-[3rem] shadow-2xl border border-slate-100 overflow-hidden aspect-[9/19] max-w-[320px] mx-auto transform rotate-2">
              <div className="bg-slate-50 rounded-[2.5rem] h-full overflow-hidden flex flex-col items-center justify-center p-8">
                <div className="w-24 h-24 bg-blue-100 rounded-full flex items-center justify-center mb-6">
                  <Droplets className="text-blue-500 w-12 h-12 animate-pulse" />
                </div>
                <div className="w-full h-1 bg-slate-200 rounded-full mb-8 overflow-hidden">
                  <motion.div 
                    initial={{ width: 0 }}
                    whileInView={{ width: "65%" }}
                    transition={{ duration: 1.5, delay: 1 }}
                    className="h-full bg-blue-500" 
                  />
                </div>
                <p className="text-2xl font-black text-slate-800">65%</p>
                <p className="text-sm text-slate-400 font-medium mt-1">יעד יומי הושג</p>
              </div>
            </div>
          </motion.div>
        </section>

        {/* Stats / Proof */}
        <section id="features" className="bg-slate-900 py-24 text-white overflow-hidden">
          <div className="max-w-7xl mx-auto px-6 grid md:grid-cols-4 gap-12">
            {[
              { icon: <Bell className="text-blue-400" />, title: "תזכורות חכמות", text: "אף פעם אל תשכח לשתות שוב עם התראות מותאמות אישית." },
              { icon: <Smartphone className="text-blue-400" />, title: "ממשק אינטראקטיבי", text: "בקבוק מים דינמי שמתמלא ככל שאתה שותה יותר." },
              { icon: <RefreshCw className="text-blue-400" />, title: "איפוס אוטומטי", text: "התקדמות יומית שמתאפסת בכל בוקר בשעה שתבחר." },
              { icon: <ShieldCheck className="text-blue-400" />, title: "פרטיות מלאה", text: "הנתונים שלך נשמרים מקומית על המכשיר בלבד." }
            ].map((feature, i) => (
              <motion.div 
                key={i}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ delay: i * 0.1 }}
                className="flex flex-col items-start gap-4 p-6 border border-slate-800 rounded-3xl hover:bg-slate-800/50 transition-colors"
              >
                <div className="p-3 bg-slate-800 rounded-2xl">{feature.icon}</div>
                <h3 className="text-xl font-bold">{feature.title}</h3>
                <p className="text-slate-400 leading-relaxed text-sm">{feature.text}</p>
              </motion.div>
            ))}
          </div>
        </section>

        {/* CTA Section */}
        <section id="download" className="max-w-5xl mx-auto px-6 py-32 text-center">
          <div className="bg-blue-600 rounded-[3rem] p-12 md:p-24 text-white shadow-2xl shadow-blue-200 relative overflow-hidden">
            <div className="absolute top-0 right-0 p-12 opacity-10">
              <Droplets className="w-64 h-64 rotate-12" />
            </div>
            <h2 className="text-4xl md:text-5xl font-extrabold mb-6 relative z-10">התחל לשתות נכון היום.</h2>
            <p className="text-xl text-blue-100 mb-12 max-w-2xl mx-auto relative z-10 leading-relaxed">
              הצטרף לאלפי משתמשים שכבר משפרים את הבריאות שלהם בעזרת הרגלי שתייה נכונים. בחינם, ללא פרסומות וללא שטויות.
            </p>
            <div className="flex flex-col sm:flex-row justify-center gap-6 relative z-10">
              <a 
                href={downloadUrl}
                download="MoonWater.apk"
                className="bg-white text-blue-600 hover:bg-blue-50 px-12 py-5 rounded-2xl font-bold text-xl transition-all active:scale-95 flex items-center justify-center gap-2"
              >
                <Download className="w-6 h-6" />
                הורד APK בחינם
              </a>
            </div>
          </div>
        </section>
      </main>

      <footer className="bg-white border-t border-slate-100 py-16">
        <div className="max-w-7xl mx-auto px-6 flex flex-col md:flex-row justify-between items-center gap-8">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
              <Droplets className="text-white w-5 h-5" />
            </div>
            <span className="text-xl font-bold text-slate-800">MoonWater</span>
          </div>
          <p className="text-slate-400 text-sm flex items-center gap-2">
            נוצר עם <Heart className="w-4 h-4 text-red-400 fill-red-400" /> בשביל הבריאות שלך
          </p>
          <div className="flex gap-8 text-sm font-medium text-slate-500">
            <span>&copy; {new Date().getFullYear()}</span>
            <a href="#" className="hover:text-blue-600 transition-colors">תנאי שימוש</a>
            <a href="#" className="hover:text-blue-600 transition-colors">פרטיות</a>
          </div>
        </div>
      </footer>
    </div>
  );
}

