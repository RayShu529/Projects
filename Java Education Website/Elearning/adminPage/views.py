import re
from django.http import HttpResponseRedirect, JsonResponse
from django.shortcuts import render
from django.urls import reverse
from datetime import datetime, timedelta
import pytz
from loginRegister.models import User
from .models import Teacher
from .models import Lesson
from .models import QuizQuestion
from .models import AssessmentQuestion
from userPage.models import QuizScore
from userPage.models import AssessmentScore
from userPage.models import ActivityLog
from django.views.decorators.csrf import csrf_exempt
from django.core.serializers import serialize
# Create your views here.
def teacherLogout(request):
    request.session['username'] = None
    return HttpResponseRedirect(reverse('login'))
def teacherPage(request):
    username = request.session.get('username')
    if username:
        try:
            teacher = Teacher.objects.get(username=username)
            template = 'teacherPage.html'
            alert_message = request.session.pop('message', None)
                
            return render(request,template,{'teacher':teacher,'alert_message': alert_message})
        except Teacher.DoesNotExist:
            return HttpResponseRedirect(reverse('mainPage'))
    else:
        return HttpResponseRedirect(reverse('login'))
def teacherProfile(request):
    template = 'teacherProfile.html'
    username = request.session.get('username')
    teacher = Teacher.objects.get(username=username)
    context = {
        'teacher' : teacher,
    }
    return render(request,template,context)

def getlesson(request):

    #lesson_names = Lesson.objects.values_list('name', flat=True)
    #result = {'result': list(lesson_names)         }
    #return JsonResponse(result)

    lesson_names = Lesson.objects.values_list('name', flat=True)

# Define a custom sorting key function
    def extract_number_and_name(name):
        match = re.match(r'lesson (\d+): (.+)', name)
        if match:
            number = int(match.group(1))
            lesson_name = match.group(2)
            return number, lesson_name
        else:
            return float('inf'), name 
    sorted_lessons = sorted(lesson_names, key=extract_number_and_name)
    result = {'result': sorted_lessons}

    return JsonResponse(result)
def getquiz(request):
    lessonName = request.GET.get('lessonName')
    question = QuizQuestion.objects.filter(lessonName=lessonName).values_list('question', flat=True)
    answer = QuizQuestion.objects.filter(lessonName=lessonName).values_list('answer', flat=True)
    result = {'question': list(question),'answer': list(answer)
              }
    return JsonResponse(result)
def getass(request):
    lessonName = request.GET.get('lessonName')
    question = AssessmentQuestion.objects.filter(lessonName=lessonName).values_list('question', flat=True)
    answer = AssessmentQuestion.objects.filter(lessonName=lessonName).values_list('answer', flat=True)
    result = {'question': list(question),'answer': list(answer)
              }
    return JsonResponse(result)
@csrf_exempt
def savequiz(request):
    lessonName = request.POST.get('lessonName')
    question = request.POST.get('question')
    answer = request.POST.get('answer')
    try:
        QuizQuestion.objects.get(question__iexact = question)
        result = {'result':'There is already a same question in the database'
              }
    except QuizQuestion.DoesNotExist:
        try:
            AssessmentQuestion.objects.get(question__iexact=question)
            result = {'result':'There is already a same question in the database'
              }
        except AssessmentQuestion.DoesNotExist:
            saveQuiz = QuizQuestion()
            saveQuiz.lessonName = lessonName
            saveQuiz.question = question
            saveQuiz.answer = answer
            saveQuiz.save()
            result = {'result':'true'
              }
    
    return JsonResponse(result)
@csrf_exempt
def saveass(request):
    lessonName = request.POST.get('lessonName')
    question = request.POST.get('question')
    answer = request.POST.get('answer')
    try:
        AssessmentQuestion.objects.get(question__iexact = question)
        result = {'result':'There is already a same question in the database'
              }
    except AssessmentQuestion.DoesNotExist:
        try:
            QuizQuestion.objects.get(question__iexact=question)
            result = {'result':'There is already a same question in the database'
              }
        except QuizQuestion.DoesNotExist:
            saveAss = AssessmentQuestion()
            saveAss.lessonName = lessonName
            saveAss.question = question
            saveAss.answer = answer
            saveAss.save()
            result = {'result':'true'
              }
    
    return JsonResponse(result)
@csrf_exempt
def editquiz(request):
    question = request.POST.get('question')
    quiz = QuizQuestion.objects.get(question=question)
    result = {'question':quiz.question,'answer':quiz.answer
              }
    return JsonResponse(result)
@csrf_exempt
def editass(request):
    question = request.POST.get('question')
    ass = AssessmentQuestion.objects.get(question=question)
    result = {'question':ass.question,'answer':ass.answer
              }
    return JsonResponse(result)
@csrf_exempt
def saveeditquiz(request):
    question = request.POST.get('question')
    oldquestion = request.POST.get('oldquestion')
    answer = request.POST.get('answer')
    try:
        quiz = QuizQuestion.objects.get(question__iexact=question)
        if quiz.answer == answer:
            result = {'result':'There is already a same question in the database'
              }
        else:
            if question==oldquestion:
                quiz.question = question
                quiz.answer = answer
                quiz.save()
                result = {'result':'true'
              }
            else:
                result = {'result':'There is already a same question in the database'
              }
    except QuizQuestion.DoesNotExist:
        try:
            AssessmentQuestion.objects.get(question__iexact = question)
            result = {'result':'There is already a same question in the database'
              }
        except AssessmentQuestion.DoesNotExist:
            
            quiz = QuizQuestion.objects.get(question=oldquestion)
            quiz.question = question
            quiz.answer = answer
            quiz.save()
            result = {'result':'true'
              }
    return JsonResponse(result)
@csrf_exempt
def saveeditass(request):
    question = request.POST.get('question')
    oldquestion = request.POST.get('oldquestion')
    answer = request.POST.get('answer')
    try:
        QuizQuestion.objects.get(question__iexact=question)
        result = {'result':'There is already a same question in the database'
              }
    except QuizQuestion.DoesNotExist:
        try:
            assess = AssessmentQuestion.objects.get(question__iexact = question)
            if assess.answer==answer:
                result = {'result':'There is already a same question in the database'
              }
            else:
                if question==oldquestion:
                    assess.question = question
                    assess.answer = answer
                    assess.save()
                    result = {'result':'true'
                    }
                else:
                    result = {'result':'There is already a same question in the database'
              }
        except AssessmentQuestion.DoesNotExist:
            ass = AssessmentQuestion.objects.get(question=oldquestion)
            ass.question = question
            ass.answer = answer
            ass.save()
            result = {'result':'true'
              }
    return JsonResponse(result)
@csrf_exempt
def deleteeditquiz(request):
    question = request.POST.get('question')
    quiz = QuizQuestion.objects.get(question=question)
    quiz.delete()
    result = {'result':'Question deleted successfully'
              }
    return JsonResponse(result)
@csrf_exempt
def deleteeditass(request):
    question = request.POST.get('question')
    ass = AssessmentQuestion.objects.get(question=question)
    ass.delete()
    result = {'result':'Question deleted successfully'
              }
    return JsonResponse(result)
def getuser(request):
    certified = []
    user_names = User.objects.values_list('username', flat=True)
    for user in user_names:
        u = User.objects.get(username = user)
        if u.certificate:
            certified.append('true')
        else:
            certified.append('')

    result = {'result': list(user_names),'certified':certified
              }
    return JsonResponse(result)
def lessoname(request):
    name = request.GET.get('lessoname',None)
    try:
        lessoname = Lesson.objects.get(name__iexact=name)
        result = {'result':'Lesson name already taken'
              }
    except Lesson.DoesNotExist:
        result = {'result':'true'
              }
    return JsonResponse(result)
@csrf_exempt
def savelesson(request):
    name = request.POST.get('name')
    info = request.POST.get('info')
    example = request.POST.get('example')
    file = request.FILES.get('file')
    video = request.FILES.get('video')
    try:
        Lesson.objects.get(name=name)
        result = {'result': f'There is already a lesson for {name}'
              }
    except Lesson.DoesNotExist:
        lesson = Lesson()
        lesson.name = name
        lesson.info = info
        lesson.example = example
        lesson.file = file
        lesson.multimedia = video
        lesson.save()
        result = {'result': 'true'
              }
    return JsonResponse(result)
@csrf_exempt
def editlesson(request):
    name = request.POST.get('name')
    oldname = request.POST.get('oldname')
    info = request.POST.get('info')
    example = request.POST.get('example')
    file = request.FILES.get('file')
    video = request.FILES.get('video')
    lesson = Lesson.objects.get(name=oldname)
    lesson.name = name
    lesson.info = info
    lesson.example = example
    if lesson:
        lesson.file.delete()
        lesson.file = file
    if video:
        lesson.multimedia.delete()
        lesson.multimedia = video
    lesson.save()
    AssessmentQuestion.objects.filter(lessonName = oldname).update(lessonName=name)
    QuizQuestion.objects.filter(lessonName = oldname).update(lessonName=name)
    QuizScore.objects.filter(lessonName = oldname).update(lessonName=name)
    AssessmentQuestion.objects.filter(lessonName = oldname).update(lessonName=name)
    ActivityLog.objects.filter(lesson = oldname).update(lesson=name)
    
    result = {'result': 'true'
              }
    return JsonResponse(result)
def editgetlesson(request):
    name = request.GET.get('lessoname',None)
    try:
        lesson = Lesson.objects.get(name=name)
        lesson_json = serialize('json', [lesson]) 
        result = {'result': lesson_json}
    except Lesson.DoesNotExist:
        result = {'result': 'false'}
    return JsonResponse(result)

def editdeletelesson(request):
    name = request.GET.get('lessoname',None)
    lesson = Lesson.objects.get(name=name)
    if lesson.file:
        lesson.file.delete()
    if lesson.multimedia:
        lesson.multimedia.delete()
    lesson.delete()
    quiz = QuizQuestion.objects.filter(lessonName=name)
    quiz.delete()
    ass = AssessmentQuestion.objects.filter(lessonName=name)
    ass.delete()
    q = QuizScore.objects.filter(lessonName=name)
    q.delete()
    a = AssessmentScore.objects.filter(lessonName=name)
    a.delete()
    result = {'result': 'true'}
    return JsonResponse(result)
def checkUser(request):
    user = User.objects.get(username=request.GET.get('username'))
    lessonList = list(Lesson.objects.values_list('name', flat=True))
    quizScores = []
    assessmentScores = []  
    for lesson in lessonList:
        try:
            quiz = QuizScore.objects.get(lessonName=lesson,username=user.username)
            quizScores.append(quiz.score)
        except QuizScore.DoesNotExist:
            quizScores.append(0)
        try:
            ass = AssessmentScore.objects.get(lessonName=lesson,username=user.username)
            assessmentScores.append(ass.score)
        except AssessmentScore.DoesNotExist:
            assessmentScores.append(0)
    if user.profilepic:
        url = user.profilepic.url
    else:
        url=""
    if user.certificate:
        certified = 'true'
    else:
        certified = None
    result={
        'certified':certified,
        'username':user.username,
        'fname':user.fname,
        'lname':user.lname,
        'pic':url,
        'email':user.email,
        'lessonList':lessonList,
        'quizScores':quizScores,
        'assScores':assessmentScores
    }
    return JsonResponse(result)
def deleteUser(request):
    username = request.GET.get('username')
    password = request.GET.get('password')
    
    try:
        Teacher.objects.get(password=password)
        user = User.objects.get(username=username)
        if user.profilepic:
            user.profilepic.delete()
        user.delete()
        QuizScore.objects.filter(username=username).delete()
        AssessmentScore.objects.filter(username=username).delete()
        ActivityLog.objects.filter(username=username).delete()
        result = {'result':'true'
                  }
    except Teacher.DoesNotExist:
        result = {'result':'Incorrect Password'
                  }
    return JsonResponse(result)
def activityLog(request):
    ph_timezone = pytz.timezone('Asia/Manila')
    activityLog = ActivityLog.objects.all()
    username =[]
    action =[]
    lesson =[]
    date =[]
    time =[]
    for log in activityLog:
        username.append(log.username)
        action.append(log.action)
        lesson.append(log.lesson)
        date.append(log.date.astimezone(ph_timezone).strftime('%Y-%m-%d'))
        time.append(log.date.astimezone(ph_timezone).strftime('%H:%M:%S'))
    distinct_actions = ActivityLog.objects.values_list('action', flat=True).distinct().order_by('action')
    result = {
        'distinct':list(distinct_actions),
        'username':username,
        'action':action,
        'lesson':lesson,
        'date':date,
        'time':time
        }
    return JsonResponse(result)
def sortDate(request):
    ph_timezone = pytz.timezone('Asia/Manila')
    action = request.GET.get("action",None)
    if action=="all":
        action=None
    sort = request.GET.get("sort",None)
    start = request.GET.get("start",None) 
    start_date = datetime.strptime(start, '%Y-%m-%d').replace(hour=0, minute=0, second=0) if start else None
    if sort=='custom':
        end = request.GET.get("end",None) 
        end_date = datetime.strptime(end, '%Y-%m-%d') if end else None
        end_date = end_date.replace(hour=23, minute=59, second=59) if end_date else None  
    elif sort == "day":
         end_date = (start_date + timedelta(days=1)).replace(hour=0, minute=0, second=0) - timedelta(seconds=1)
    elif sort == "week":
        end_date = start_date + timedelta(weeks=1) if start_date else None
    elif sort == "month":
        end_date = start_date + timedelta(days=30) if start_date else None
    elif sort == "year":
        end_date = start_date + timedelta(days=365) if start_date else None
    if start_date and end_date and action:
        activityLog = ActivityLog.objects.filter(date__range=[start_date , end_date],action=action)
    elif start_date and end_date:
        activityLog = ActivityLog.objects.filter(date__range=[start_date , end_date])
    elif action is not None:
        activityLog = ActivityLog.objects.filter(action=action)
    else:
        activityLog = ActivityLog.objects.all()
    username =[]
    action =[]
    lesson =[]
    date =[]
    time =[]
    for log in activityLog:
        username.append(log.username)
        action.append(log.action)
        lesson.append(log.lesson)
        date.append(log.date.astimezone(ph_timezone).strftime('%Y-%m-%d'))
        time.append(log.date.astimezone(ph_timezone).strftime('%H:%M:%S'))
    result = {
        'username':username,
        'action':action,
        'lesson':lesson,
        'date':date,
        'time':time
        }
    return JsonResponse(result)
