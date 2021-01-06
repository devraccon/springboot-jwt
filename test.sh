echo "deployment images version :  $1"
kubectl set image deployment/my-react-app my-react-app=kangzu8743/apitest:v$1
echo "deploy success"
kubectl get pods
exit 0