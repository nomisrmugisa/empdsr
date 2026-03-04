# 🚀 Deploying EMPDSR New App to Render.com

## Prerequisites
1. **GitHub Account** - Your code needs to be in a GitHub repository
2. **Render.com Account** - Sign up at https://render.com (free tier available)

## 📁 Files Created for Deployment
- `Dockerfile` - Updated for cloud deployment
- `render.yaml` - Render service configuration
- `application-production.properties` - Production settings
- `.dockerignore` - Optimizes Docker build
- `start.sh` - Startup script with proper initialization

## 🔧 Deployment Steps

### Step 1: Push to GitHub
```bash
# Initialize git repository (if not already done)
git init
git add .
git commit -m "Initial commit - EMPDSR new app"

# Add your GitHub repository
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
git branch -M main
git push -u origin main
```

### Step 2: Deploy on Render.com

#### Option A: Using render.yaml (Recommended)
1. Go to https://render.com/dashboard
2. Click **"New"** → **"Blueprint"**
3. Connect your GitHub repository
4. Select the repository containing your app
5. Render will automatically detect the `render.yaml` file
6. Click **"Apply"** to deploy

#### Option B: Manual Web Service Creation
1. Go to https://render.com/dashboard
2. Click **"New"** → **"Web Service"**
3. Connect your GitHub repository
4. Configure:
   - **Name**: `empdsr-new`
   - **Environment**: `Docker`
   - **Region**: `Oregon` (or closest to you)
   - **Branch**: `main`
   - **Dockerfile Path**: `./Dockerfile`
   - **Plan**: `Free` (for testing)

### Step 3: Environment Variables (Optional)
Add these in Render dashboard under "Environment":
- `JAVA_OPTS`: `-Xmx512m -Xms256m`
- `SPRING_PROFILES_ACTIVE`: `production`

## 🌐 Access Your App
Once deployed, Render will provide a URL like:
`https://empdsr-new.onrender.com`

## 🔍 Key Features Configured
- ✅ **Dynamic Port**: Uses Render's `$PORT` environment variable
- ✅ **Production Profile**: Optimized settings for cloud
- ✅ **H2 Database**: File-based storage (persists between deployments)
- ✅ **Memory Optimization**: JVM tuned for free tier limits
- ✅ **Security**: H2 console disabled in production
- ✅ **Logging**: Reduced verbosity for production

## 📊 Expected Performance
- **Cold Start**: ~30-60 seconds (free tier)
- **Memory Usage**: ~256-512MB
- **Database**: H2 file-based (suitable for testing)

## 🐛 Troubleshooting
1. **Build Fails**: Check build logs in Render dashboard
2. **App Won't Start**: Verify environment variables
3. **Database Issues**: H2 files are created automatically
4. **Port Issues**: Render automatically assigns PORT variable

## 🔄 Auto-Deployment
The app will automatically redeploy when you push to the `main` branch on GitHub.

## 💡 Next Steps for Production
For production use, consider:
- Upgrading to a paid Render plan
- Using PostgreSQL instead of H2
- Setting up proper environment variables for DHIS2 credentials
- Configuring custom domain
