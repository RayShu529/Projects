from django.db import models

# Create your models here.
class Teacher(models.Model):
    fname = models.CharField(max_length=50, verbose_name= 'First Name', name='fname')
    lname = models.CharField(max_length=50, verbose_name= 'Last Name', name='lname')
    username = models.CharField(max_length=50,default='',verbose_name= 'Username', name='username')
    email = models.EmailField(max_length=50,default='', verbose_name= 'Email', name='email')
    password = models.CharField(max_length=50,default='', verbose_name= 'Password', name='password')
    profilepic = models.ImageField(null=True, blank=True, upload_to='images/', verbose_name='profilepic')
    report = models.FileField(null=True, blank=True, upload_to='reports/', verbose_name='report')
    def __str__(self):
        return f"{self.username}"
class Lesson(models.Model):
    name = models.CharField(max_length=50,verbose_name= 'Lesson Name', name='name')
    info = models.TextField(verbose_name= 'Lesson Info', name='info')
    example = models.TextField(null=True,blank=True,verbose_name= 'Lesson Exercise', name='exercise')
    multimedia = models.FileField(null=True,blank=True,upload_to='medialesson/', verbose_name='multimedia')
    file = models.FileField(null=True,blank=True,upload_to='mediafiles/', verbose_name='file')
    def __str__(self):
        return f"{self.name}"
class QuizQuestion(models.Model):
    lessonName = models.CharField(max_length=255,verbose_name= 'Lesson Name', name='lessonName')
    question = models.TextField(verbose_name= 'Question', name='question')
    answer = models.CharField(max_length=255,verbose_name= 'Answer', name='answer')
    def __str__(self):
        return f"{self.lessonName}"
class AssessmentQuestion(models.Model):
    lessonName = models.CharField(max_length=255,verbose_name= 'Lesson Name', name='lessonName')
    question = models.TextField(verbose_name= 'Question', name='question')
    answer = models.CharField(max_length=255,verbose_name= 'Answer', name='answer')
    def __str__(self):
        return f"{self.lessonName}"
    