# 🔍 Render.com Deployment Verification Checklist

## After Deployment Completes

### ✅ **Basic Functionality Tests**
- [ ] App loads at your Render URL (e.g., `https://empdsr-new.onrender.com`)
- [ ] Login page appears correctly
- [ ] Can log in with existing credentials
- [ ] Home dashboard loads without errors

### ✅ **DHIS2 Sync Page Tests**
- [ ] Navigate to `/registry/dhis2`
- [ ] Page loads with both sections:
  - [ ] "Show/Hide JSON Payload Debug" button (gray header)
  - [ ] "Show/Hide DHIS2 DataElement Mapping Reference" button (blue header)

### ✅ **DataElement Mapping Reference**
- [ ] Click "Show/Hide DHIS2 DataElement Mapping Reference"
- [ ] Verify it shows:
  - [ ] 4 DHIS2 Programs (Maternal/Perinatal Notification & Review)
  - [ ] Fixed orgUnit: `MJu9Pwi3twb`
  - [ ] 47 DataElements organized by categories
  - [ ] Color-coded badges for different programs

### ✅ **DHIS2 Sync Testing**
- [ ] Select a case for sync
- [ ] Click "Sync to DHIS2"
- [ ] Verify JSON payload appears in debug section
- [ ] Check that event ID is DHIS2-compliant (starts with letter)
- [ ] Confirm orgUnit is hardcoded to `MJu9Pwi3twb`

### ✅ **Performance Check**
- [ ] App responds within reasonable time (cold start may take 30-60 seconds)
- [ ] No memory errors in Render logs
- [ ] Database operations work correctly

## 🐛 **Troubleshooting Common Issues**

### If Build Fails:
1. Check Render build logs for specific errors
2. Verify Dockerfile syntax
3. Ensure all required files are in the repository

### If App Won't Start:
1. Check Render service logs
2. Verify environment variables are set correctly
3. Ensure PORT environment variable is being used

### If Database Issues:
1. H2 database files should be created automatically
2. Check if `/app/data` and `/app/slave` directories exist
3. Verify database connection strings in production properties

### If DHIS2 Sync Fails:
1. Check network connectivity from Render to DHIS2 server
2. Verify DHIS2 credentials and endpoints
3. Review JSON payload for formatting issues

## 📊 **Expected Performance Metrics**
- **Cold Start Time**: 30-60 seconds (first request after idle)
- **Warm Response Time**: 1-3 seconds
- **Memory Usage**: ~256-512MB
- **Build Time**: 5-10 minutes

## 🔄 **Auto-Deployment**
- Any push to `new_main_empdsr` branch will trigger automatic redeployment
- Build logs available in Render dashboard
- Zero-downtime deployment on Render's paid plans

## 📞 **Support**
If you encounter issues:
1. Check Render service logs first
2. Review build logs for compilation errors
3. Verify GitHub webhook is working for auto-deploy
