import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Droplets, Settings, Download, Smartphone, Check, Clock } from 'lucide-react';

const MoonWaterLanding = () => {
  const [activeTab, setActiveTab] = useState('home');
  const [progress, setProgress] = useState(75);
  const [intake, setIntake] = useState(1500);
  const goal = 2000;

  useEffect(() => {
    const interval = setInterval(() => {
      setProgress((prev) => (prev >= 100 ? 75 : prev + 0.05));
    }, 2000);
    return () => clearInterval(interval);
  }, []);

  const addWater = (amount: number) => {
    setIntake((prev) => Math.min(prev + amount, goal));
    setProgress((prev) => Math.min((intake + amount) / goal * 100, 100));
  };

  return (
    <div className="min-h-screen bg-slate-900 font-sans text-slate-300 overflow-x-hidden selection:bg-sky-500/30" dir="rtl">
      {/* Landing Page Content */}
      <div className="max-w-7xl mx-auto px-6 lg:px-8 py-12 min-h-screen flex flex-col lg:flex-row items-center justify-center gap-12 lg:gap-24">
        
        {/* Left Side: Marketing */}
        <motion.div 
          initial={{ opacity: 0, x: 20 }}
          animate={{ opacity: 1, x: 0 }}
          className="flex-1 space-y-8 text-right"
        >
          <div className="space-y-4">
            <h1 className="text-6xl lg:text-8xl font-black tracking-tight text-white leading-tight">
              MoonWater
            </h1>
            <p className="text-xl lg:text-2xl text-slate-400 font-light max-w-lg leading-relaxed">
              הדרך הפשוטה והחכמה להישאר רעננים לאורך כל היום. עקבו אחרי שתיית המים שלכם בשיטה הטבעית ביותר.
            </p>
          </div>

          <div className="bg-slate-800/50 backdrop-blur-md p-8 rounded-[2rem] border border-slate-700/50 space-y-6 shadow-2xl relative overflow-hidden group">
            <div className="absolute top-0 right-0 w-32 h-32 bg-sky-500/10 rounded-full blur-3xl group-hover:bg-sky-500/15 transition-all" />
            <h3 className="text-xl font-bold text-white flex items-center gap-3">
              <div className="w-2 h-2 rounded-full bg-sky-400" />
              איך להשתמש באפליקציה?
            </h3>
            <ul className="text-slate-400 space-y-4 text-sm lg:text-base">
              <li className="flex gap-4">
                <span className="flex-shrink-0 w-6 h-6 rounded-lg bg-slate-700 flex items-center justify-center text-xs font-bold text-sky-400">1</span>
                <span>הורידו את קובץ ה-APK מהכפתור למטה</span>
              </li>
              <li className="flex gap-4">
                <span className="flex-shrink-0 w-6 h-6 rounded-lg bg-slate-700 flex items-center justify-center text-xs font-bold text-sky-400">2</span>
                <span>אשרו התקנה ממקורות לא ידועים בהגדרות המכשיר</span>
              </li>
              <li className="flex gap-4">
                <span className="flex-shrink-0 w-6 h-6 rounded-lg bg-slate-700 flex items-center justify-center text-xs font-bold text-sky-400">3</span>
                <span>התקינו והתחילו לשתות! האפליקציה פועלת אופליין לחלוטין</span>
              </li>
            </ul>
            <a 
              href="https://example.com/moonwater.apk" 
              className="flex items-center justify-center gap-3 bg-sky-500 hover:bg-sky-400 text-white px-8 py-5 rounded-2xl font-black transition-all shadow-xl shadow-sky-500/20 active:scale-[0.98] group"
            >
              <Download className="w-6 h-6 group-hover:animate-bounce" />
              <span>הורד APK עכשיו (v1.0.0)</span>
            </a>
          </div>

          <div className="flex flex-wrap gap-4">
            <div className="bg-slate-800/30 px-6 py-4 rounded-2xl border border-slate-700/30 flex items-center gap-3">
              <Smartphone className="text-sky-400 w-5 h-5" />
              <div className="text-right">
                <span className="block text-[10px] uppercase font-bold text-slate-500 tracking-widest">תמיכה</span>
                <span className="text-sm font-semibold text-slate-300">Android 8.0+</span>
              </div>
            </div>
            <div className="bg-slate-800/30 px-6 py-4 rounded-2xl border border-slate-700/30 flex items-center gap-3">
              <div className="w-5 h-5 rounded-full border-2 border-sky-400 flex items-center justify-center">
                <Check className="text-sky-400 w-3 h-3" />
              </div>
              <div className="text-right">
                <span className="block text-[10px] uppercase font-bold text-slate-500 tracking-widest">פרטיות</span>
                <span className="text-sm font-semibold text-slate-300">100% אופליין</span>
              </div>
            </div>
          </div>
        </motion.div>

        {/* Right Side: Mock App */}
        <motion.div 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          className="relative flex-shrink-0 w-[350px] h-[720px] bg-slate-950 rounded-[4rem] border-[12px] border-slate-800 shadow-[0_0_120px_rgba(14,165,233,0.2)] ring-1 ring-slate-700/50"
        >
          {/* Dynamic Island Notch */}
          <div className="absolute top-0 left-1/2 -translate-x-1/2 w-32 h-8 bg-slate-800 rounded-b-3xl z-40" />

          {/* Screen Content */}
          <div className="relative h-full w-full bg-white rounded-[3.25rem] overflow-hidden flex flex-col">
            <div className="p-8 pt-16 flex items-center justify-between">
              <div className="space-y-1">
                <p className="text-[10px] text-slate-400 font-bold uppercase tracking-widest">יום שישי, 24 באפריל</p>
                <h2 className="text-3xl font-black text-slate-900">MoonWater</h2>
              </div>
              <button className="w-12 h-12 bg-slate-100 rounded-2xl flex items-center justify-center text-slate-500">
                <Settings className="w-6 h-6" />
              </button>
            </div>

            <div className="flex-1 px-8 space-y-10">
              {/* Progress */}
              <div className="relative w-56 h-56 mx-auto">
                <svg className="w-full h-full -rotate-90">
                  <circle cx="112" cy="112" r="100" stroke="#F1F5F9" strokeWidth="16" fill="transparent" />
                  <motion.circle 
                    cx="112" cy="112" r="100" 
                    stroke="#0EA5E9" 
                    strokeWidth="16" 
                    fill="transparent" 
                    strokeDasharray="628.3" 
                    initial={{ strokeDashoffset: 628.3 }}
                    animate={{ strokeDashoffset: 628.3 * (1 - progress / 100) }}
                    strokeLinecap="round"
                    transition={{ duration: 1 }}
                  />
                </svg>
                <div className="absolute inset-0 flex flex-col items-center justify-center text-center">
                  <span className="text-6xl font-black text-slate-900">{Math.round(progress)}%</span>
                  <div className="flex flex-col">
                    <span className="text-[10px] uppercase font-bold text-slate-400 tracking-tighter">נצרך היום</span>
                    <span className="text-sky-500 font-bold">{intake} מ״ל</span>
                  </div>
                </div>
              </div>

              <div className="space-y-4">
                <div className="bg-slate-50 p-5 rounded-3xl flex items-center gap-4 border border-slate-100">
                  <div className="w-12 h-12 bg-sky-100 rounded-2xl flex items-center justify-center text-sky-600 shadow-sm">
                    <Clock className="w-6 h-6" />
                  </div>
                  <div>
                    <p className="text-[10px] text-slate-400 font-bold uppercase">התזכורת הבאה</p>
                    <p className="text-lg font-black text-slate-900 tracking-tight italic">בעוד 42 דקות</p>
                  </div>
                </div>

                <button 
                  onClick={() => addWater(250)}
                  className="w-full bg-sky-500 hover:bg-sky-400 text-white py-5 rounded-[2rem] font-black text-xl shadow-xl shadow-sky-100 transition-all active:scale-[0.98]"
                >
                  שתיתי מים
                </button>
              </div>
            </div>

            {/* Bottom Nav */}
            <div className="p-6 bg-slate-50 border-t border-slate-100 flex justify-around rounded-b-[3.25rem]">
              <button className="flex flex-col items-center gap-1 text-sky-500">
                <Smartphone className="w-6 h-6" />
                <span className="text-[9px] font-black">בית</span>
              </button>
              <button className="flex flex-col items-center gap-1 text-slate-400">
                <Droplets className="w-6 h-6" />
                <span className="text-[9px] font-black">היסטוריה</span>
              </button>
              <button className="flex flex-col items-center gap-1 text-slate-400">
                <Settings className="w-6 h-6" />
                <span className="text-[9px] font-black">הגדרות</span>
              </button>
            </div>
          </div>
        </motion.div>
      </div>

      <style>{`
        body { background-color: #0f172a; }
      `}</style>
    </div>
  );
};

export default function App() {
  return <MoonWaterLanding />;
}
