FROM python:latest
RUN mkdir myapp/
COPY main.py myapp/main.py
COPY requirements.txt myapp/requirements.txt
WORKDIR /myapp/
RUN pip install -r requirements.txt
EXPOSE 9000
CMD ["python", "app.py"]