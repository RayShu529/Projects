import json
import os
from django.conf import settings
from random import sample
from django.utils import timezone
from django.http import HttpResponseRedirect, JsonResponse
from django.shortcuts import render
from django.urls import reverse
import pytz
from loginRegister.models import User
from django.views.decorators.csrf import csrf_exempt
from django.core.validators import validate_email
from django.core.exceptions import ValidationError
from adminPage.models import Teacher
from adminPage.models import QuizQuestion
from adminPage.models import AssessmentQuestion
from .models import QuizScore
from .models import AssessmentScore
from .models import ActivityLog
from adminPage.models import Lesson
import cv2

# Create your views here.
def log(request):
    lesson = request.GET.get('lessoname')
    action = request.GET.get('action')
    date = timezone.now()
    activity = ActivityLog()
    activity.username = request.session.get('username')
    activity.action = action
    activity.lesson = lesson
    activity.date = date
    activity.save()

def mainpage(request):
    username = request.session.get('username')
    if username:
        try:
            user = User.objects.get(username=username)
            template = 'Main.html'
            alert_message = request.session.pop('message', None)
            
            if user.certificate:
                certified = "true"
            else:
                certified=None
            return render(request,template,{'certified':certified,'user':user,'alert_message': alert_message})
        except User.DoesNotExist:
            return HttpResponseRedirect(reverse('teacherpage'))
        
    else:
        return HttpResponseRedirect(reverse('login'))
def userlogout(request):
    date = timezone.now()
    activity = ActivityLog()
    activity.username = request.session.get('username')
    activity.action = 'Log Out'
    activity.lesson = ''
    activity.date = date
    activity.save()
    request.session['username'] = None
    return HttpResponseRedirect(reverse('login'))
def editProfile(request):
    template = 'Profile.html'
    username = request.session.get('username')
    user = User.objects.get(username=username)
    if user.certificate:
        certified = "true"
    else:
        certified = None
    context = {
        'user' : user,
        'certified':certified
    }
    return render(request,template,context)
def changepassword(request):
    username = request.session.get('username')
    password = request.GET['password']
    newpassword = request.GET['newpassword']
    try:
        user = User.objects.get(username=username, password=password)
        user.password = newpassword
        user.save()
        date = timezone.now()
        activity = ActivityLog()
        activity.username = username
        activity.action = 'Change Password'
        activity.lesson = ''
        activity.date = date
        activity.save()
        result = {'result':'true'
                  }
    except User.DoesNotExist:
        try:
            teacher = Teacher.objects.get(username=username, password=password)
            teacher.password = newpassword
            teacher.save()
            result = {'result':'true'
                  }
        except Teacher.DoesNotExist:
            result = {'result':'Incorrect Password'
                  }
    return JsonResponse(result)
def changeemail(request):
    username = request.session.get('username')
    password = request.GET['password']
    email = request.GET['email']
    try:
        validate_email(email)
        if User.objects.filter(email__iexact=email).exists() or Teacher.objects.filter(email__iexact=email).exists():
            result = {'result': 'Email is already taken'}
        else:
            try:
                user = User.objects.get(username=username, password=password)
                user.email = email
                user.save()
                date = timezone.now()
                activity = ActivityLog()
                activity.username = username
                activity.action = 'Change Email'
                activity.lesson = ''
                activity.date = date
                activity.save()
                result = {'result':'true','email':user.email,
                  }
        
            except User.DoesNotExist:
                try:
                    teacher = Teacher.objects.get(username=username, password=password)
                    teacher.email = email
                    teacher.save()
                    result = {'result':'true','email':teacher.email,
                  }
                except Teacher.DoesNotExist:
                    result = {'result':'Incorrect Password'
                  }
    except ValidationError:
        result = {'result': 'Email is not in proper format'}
    return JsonResponse(result)
@csrf_exempt
def changepicture(request):
    username = request.session.get('username')
    password = request.POST.get('password')
    picture = request.FILES.get('picture')
    try:
        user = User.objects.get(username=username, password=password)
        if user.profilepic:
            user.profilepic.delete()
        user.profilepic = picture
        user.save()
        date = timezone.now()
        activity = ActivityLog()
        activity.username = username
        activity.action = 'Change Picture'
        activity.lesson = ''
        activity.date = date
        activity.save()
        result = {'result':'true','picture':user.profilepic.url,
                  }
    except User.DoesNotExist:
        try:
            teacher = Teacher.objects.get(username=username, password=password)
            if teacher.profilepic:
                teacher.profilepic.delete()
            teacher.profilepic = picture
            teacher.save()
            result = {'result':'true','picture':teacher.profilepic.url,
                  }
        except Teacher.DoesNotExist:
            result = {'result':'Incorrect Password'
                  }
    return JsonResponse(result)
def deleteAccount(request):
    username = request.session.get('username')
    password = request.GET.get('password')
    try:
        user = User.objects.get(username=username, password=password)
        if user.profilepic:
            user.profilepic.delete()
        user.delete()
        QuizScore.objects.filter(username=username).delete()
        AssessmentScore.objects.filter(username=username).delete()
        request.session['username'] = None
        result = {'result':'true'
                  }
    except User.DoesNotExist:
        result = {'result':'Incorrect Password'
                  }
    return JsonResponse(result)
def getquiz(request):
    lessonName = request.GET.get('lessoname')
    questions = list(
        QuizQuestion.objects.filter(lessonName=lessonName).values_list('question', flat=True)
    )
    if len(questions) < 5:
        result = {'result': 'false'}
    else:
        random_questions = sample(questions, min(5, len(questions)))
        for i, question in enumerate(random_questions, 1):
            answer = QuizQuestion.objects.get(question=question)
            request.session[f'answer{i}'] = answer.answer.lower()
        result = {'result': 'true', 'question': random_questions}
    return JsonResponse(result)
def getass(request):
    lessonName = request.GET.get('lessoname')
    questions = list(
        AssessmentQuestion.objects.filter(lessonName=lessonName).values_list('question', flat=True)
    )
    if len(questions) < 5:
        result = {'result': 'false'}
    else:
        random_questions = sample(questions, min(5, len(questions)))
        for i, question in enumerate(random_questions, 1):
            answer = AssessmentQuestion.objects.get(question=question)
            request.session[f'answer{i}'] = answer.answer.lower()
        result = {'result': 'true', 'question': random_questions}
    return JsonResponse(result)
@csrf_exempt
def submitquiz(request):
    username = request.session.get('username')
    lessonName = request.POST.get('lessonName')
    answers = [request.POST.get(f'answer{i}')for i in range(1,6)]
    date = timezone.now()
    score = 0
    for i, answer in enumerate(answers, 1):
        if answer.lower()==request.session.get(f'answer{i}'):
            score+=1
    try:    
        q = QuizScore.objects.get(lessonName=lessonName,username=username)
        q.delete()
    except QuizScore.DoesNotExist:
        pass
    quizScore = QuizScore()
    quizScore.username = username
    quizScore.lessonName = lessonName
    quizScore.score = score
    quizScore.date = date
    quizScore.save()
    activity = ActivityLog()
    activity.username = username
    activity.action = "Quiz Taken"
    activity.lesson = lessonName
    activity.date = date
    activity.save()
    result = {'result':score}
    return JsonResponse(result)
@csrf_exempt
def submitass(request):
    username = request.session.get('username')
    lessonName = request.POST.get('lessonName')
    answers = [request.POST.get(f'answer{i}')for i in range(1,6)]
    date = timezone.now()
    score = 0
    for i, answer in enumerate(answers, 1):
        if answer.lower()==request.session.get(f'answer{i}'):
            score+=1
        print(answer)
        print(request.session.get(f'answer{i}'))
    try:    
        a = AssessmentScore.objects.get(lessonName=lessonName,username=username)
        a.delete()
    except AssessmentScore.DoesNotExist:
        pass
    assScore = AssessmentScore()
    assScore.username = username
    assScore.lessonName = lessonName
    assScore.score = score
    assScore.date = date
    assScore.save()
    activity = ActivityLog()
    activity.username = username
    activity.action = "Assessment Taken"
    activity.lesson = lessonName
    activity.date = date
    activity.save()
    result = {'result':score}
    return JsonResponse(result)
def getscore(request):
    lessonName = request.GET.get('lessoname')
    username = request.session.get('username')
    try:
        quiz = QuizScore.objects.get(lessonName=lessonName,username=username)
        qScore = quiz.score
    except QuizScore.DoesNotExist:
        qScore = 0
    try:
        ass = AssessmentScore.objects.get(lessonName=lessonName,username=username)
        aScore = ass.score
    except AssessmentScore.DoesNotExist:
        aScore = 0
    result = {'quiz':qScore,'ass':aScore}
    return JsonResponse(result)
def certificate(request):
    username = request.session.get('username')
    lessonList = list(Lesson.objects.values_list('name', flat=True))
    status=True
    result = {'result':'true'}
    for lesson in lessonList:
        try:
            quiz = QuizScore.objects.get(lessonName=lesson,username=username)
            if quiz.score<3:
                status = False
        except QuizScore.DoesNotExist:
            status = False
        try:
            ass = AssessmentScore.objects.get(lessonName=lesson,username=username)
            if ass.score<3:
                status = False
        except AssessmentScore.DoesNotExist:
            status = False
        if status==False:
            result = {'result':'false'}
            break
    if status==True:
        user = User.objects.get(username=username)
        if user.certificate:
            user.certificate.delete()
        name = f"{user.fname.title()} {user.lname.title()}"
        image_path = os.path.join(settings.MEDIA_ROOT, 'images', 'certificates', 'certificate.jpg')
        template = cv2.imread(image_path)
        font = cv2.FONT_HERSHEY_COMPLEX
        font_scale = 2.5
        font_thickness = 2
        color = (63, 0, 63)
        if user.datecertified:
            date=user.datecertified
            date2 = timezone.now()
        else:
            date = timezone.now().astimezone(pytz.timezone('Asia/Manila')).strftime('%Y-%m-%d')
            date2 = timezone.now()
        strdate = str(date)
        text_size = cv2.getTextSize(name, font, font_scale, font_thickness)[0]
        text_x = (template.shape[1] - text_size[0]) // 2
        cv2.putText(template,name,(text_x,765),font, font_scale, color, font_thickness,cv2.LINE_AA)
        cv2.putText(template,strdate,(1650,1300),font, 1, color, 1,cv2.LINE_AA)
        modified_image_path = os.path.join(settings.MEDIA_ROOT, 'images', 'certificates', f'certificate_{name}.jpg')
        cv2.imwrite(modified_image_path, template)
        user.certificate.name = f'images/certificates/certificate_{name}.jpg'
        user.datecertified = date
        user.save()
        activity = ActivityLog()
        activity.username = username
        activity.action = 'Certificate Download'
        activity.lesson = ''
        activity.date = date2
        activity.save()
        result = {
            'result':modified_image_path
        }
    return JsonResponse(result)
def certificateup(request):
    password = request.GET.get('password','')
    username = request.session.get('username')
    try:
        user = User.objects.get(password=password,username=username)
        user.datecertified = None
        user.save()
        QuizScore.objects.filter(username=username).delete()
        AssessmentScore.objects.filter(username=username).delete()
        result = {'result':"Data deleted successfuly. Pass all the quiz and assessment to get a new certificate"
                  }
    except User.DoesNotExist:
        result = {'result':"Incorrect password"
                  }
    return JsonResponse(result)
def getscores(request):
    username = request.session.get('username')
    lessonList = list(Lesson.objects.values_list('name', flat=True))
    quizScores = []
    assessmentScores = []  
    for lesson in lessonList:
        try:
            quiz = QuizScore.objects.get(lessonName=lesson,username=username)
            quizScores.append(quiz.score)
        except QuizScore.DoesNotExist:
            quizScores.append(0)
        try:
            ass = AssessmentScore.objects.get(lessonName=lesson,username=username)
            assessmentScores.append(ass.score)
        except AssessmentScore.DoesNotExist:
            assessmentScores.append(0)
    result={
        'lessonList':lessonList,
        'quizScores':quizScores,
        'assScores':assessmentScores
    }
    return JsonResponse(result)