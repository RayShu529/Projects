from django.utils import timezone
from django.db import models

# Create your models here.
class QuizScore(models.Model):
    username = models.CharField(max_length=50,default='',verbose_name= 'Username', name='username')
    lessonName = models.CharField(max_length=50,default='',verbose_name= 'Lesson Name', name='lessonName')
    score = models.IntegerField(default=0,verbose_name= 'Score', name='score')
    date = models.DateTimeField(default=timezone.now,verbose_name= 'Date', name='date')
    def __str__(self):
        return f"{self.username} - score in {self.lessonName}"
class AssessmentScore(models.Model):
    username = models.CharField(max_length=50,default='',verbose_name= 'Username', name='username')
    lessonName = models.CharField(max_length=50,default='',verbose_name= 'Lesson Name', name='lessonName')
    score = models.IntegerField(default=0,verbose_name= 'Score', name='score')
    date = models.DateTimeField(default=timezone.now,verbose_name= 'Date', name='date')
    def __str__(self):
        return f"{self.username} - score in {self.lessonName}"
class ActivityLog(models.Model):
    username = models.CharField(max_length=50,default='',verbose_name= 'Username', name='username')
    action = models.CharField(max_length=50,default='',verbose_name= 'Action', name='action')
    lesson = models.CharField(max_length=50,default='',verbose_name= 'Lesson', name='lesson')
    date = models.DateTimeField(default=timezone.now,verbose_name= 'Date', name='date')
    def __str__(self):
        return f"{self.username} - {self.action}  {self.lesson}"
   