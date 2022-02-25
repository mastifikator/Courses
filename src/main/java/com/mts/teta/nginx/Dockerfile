FROM nginx:1.20-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY teta /etc/nginx/sites-enabled/teta
COPY index.html /var/www/html/index.html
RUN adduser -S www-data -G www-data && chown www-data /var/www/html/index.html && chmod 755 /var/www/html/index.html
EXPOSE 8890
CMD ["nginx", "-g", "daemon off;"]