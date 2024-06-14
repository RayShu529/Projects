from django.shortcuts import render
from . import models
from .models import User
from django.http import HttpResponseRedirect, JsonResponse
from django.urls import reverse
from adminPage.models import Teacher
from userPage.models import ActivityLog
from django.utils import timezone
from django.core.validators import validate_email
from django.core.exceptions import ValidationError
# Create your views here.


def registerform(request):
    username = request.session.get('username')
    if username:
        try:
            Teacher.objects.get(username=username)
            return HttpResponseRedirect(reverse('teacherpage'))
        except Teacher.DoesNotExist:
            try:
                User.objects.get(username=username)
                return HttpResponseRedirect(reverse('mainPage'))
            except User.DoesNotExist:
                pass
    else:
        template = 'Register.html'
        context = {
        'title' : 'Register',
    }
    return render(request,template,context)
    
def usernamecheck(request):
    username = request.GET['username']
    if User.objects.filter(username__iexact=username).exists():
        result = {'result':'false'
                  }
        
        return JsonResponse(result)
    elif Teacher.objects.filter(username__iexact=username).exists():
        result = {'result':'false'
                  }
        
        return JsonResponse(result)
    else:
        result = {'result':'true'
                  }
        
        return JsonResponse(result)    
def emailcheck(request):
    email = request.GET['email']
    try:
        User.objects.get(email__iexact=email)
        result = {'result': 'Email is already taken'}
    except User.DoesNotExist:
        try:
            Teacher.objects.get(email__iexact=email)
            result = {'result': 'Email is already taken'}
        except Teacher.DoesNotExist:
            try:
                validate_email(email)
                result = {'result': 'true'}
            except ValidationError:
                result = {'result': 'Email is not in proper format'}
    
    return JsonResponse(result)    
    
def registersave(request):
    student = models.User()
    student.fname = request.GET.get('fname', '').title()
    student.lname = request.GET.get('lname', '').title()
    student.username = request.GET.get('username','')
    student.email = request.GET.get('email','')
    student.password = request.GET.get('password','')
    student.save()
    date = timezone.now()
    activity = ActivityLog()
    activity.username = request.GET.get('username','')
    activity.action = "Register"
    activity.lesson = ""
    activity.date = date
    activity.save()
    result = {'result': 'Registration successful. You can now log in.'}
    return JsonResponse(result)
    
def loginform(request):
    username = request.session.get('username')
    if username:
        try:
            Teacher.objects.get(username=username)
            return HttpResponseRedirect(reverse('teacherpage'))
        except Teacher.DoesNotExist:
            try:
                User.objects.get(username=username)
                return HttpResponseRedirect(reverse('mainPage'))
            except User.DoesNotExist:
                pass
    else:
        template = 'Landing Page.html'
        context = {
        'title' : 'Login',
    }
    return render(request,template,context)
def logcheck(request):
    username = request.GET['username']
    password = request.GET['password']
    if Teacher.objects.filter(username=username, password=password).exists():
        result = {'result':'true'
                  }
        
        return JsonResponse(result)
    elif Teacher.objects.filter(email=username, password=password).exists():
        result = {'result':'true'
                  }
        
        return JsonResponse(result)
    elif User.objects.filter(username=username, password=password).exists():
        result = {'result':'true'
                  }
        
        return JsonResponse(result)
    elif User.objects.filter(email=username, password=password).exists():
        result = {'result':'true'
                  }
        
        return JsonResponse(result)
    else:
        if User.objects.filter(username=username).exists():
            result = {'result':'Incorrect password'
                  }
        
            return JsonResponse(result)
        else:  
            result = {'result':'User not found'
                  }
        
            return JsonResponse(result)

def loginlog(request):
    username = request.GET.get('username')
    password = request.GET.get('password')
    
    if Teacher.objects.filter(username=username, password=password).exists():
        request.session['message'] = f"Welcome {username}"
        request.session['username'] = username
        result = {'result':'true',
                  'location':'/E-Learning/Teacher/Homepage/'
                  }
        
        return JsonResponse(result)
          
    elif Teacher.objects.filter(email=username, password=password).exists():
        teacher = Teacher.objects.get(email=username, password=password)
        request.session['message'] = f"Welcome {teacher.username}"
        request.session['username'] = teacher.username
        result = {'result':'true',
                  'location':'/E-Learning/Teacher/Homepage/'
                  }
        
        return JsonResponse(result)
    elif User.objects.filter(username=username, password=password).exists():
        request.session['message'] = f"Welcome {username}"
        request.session['username'] = username
        log(username,'Log In')
        result = {'result':'true',
                  'location':'/E-Learning/User/Homepage/'
                  }
        
        return JsonResponse(result)
          
    elif User.objects.filter(email=username, password=password).exists():
        user = models.User.objects.get(email=username, password=password)
        request.session['message'] = f"Welcome {user.username}"
        request.session['username'] = user.username
        log(user.username,'Log In')
        result = {'result':'true',
                  'location':'/E-Learning/User/Homepage/'
                  }
        
        return JsonResponse(result)
    else:
        result = {'result':'User not found',
                  
                  }
        
        return JsonResponse(result)
def log(name,action):
    date = timezone.now()
    activity = ActivityLog()
    activity.username = name
    activity.action = action
    activity.lesson = ""
    activity.date = date
    activity.save()
    