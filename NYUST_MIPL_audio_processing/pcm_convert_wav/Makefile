
all:
	gcc pcm_convert_wav.c -o pcm2wav.out
pcm_convert_wav.o:pcm_convert_wav.c
	gcc -c -fPIC pcm_convert_wav.c -o pcm_convert_wav.o

build:pcm_convert_wav.o
	gcc pcm_convert_wav.o -shared -o libpcm2wav.so
	rm *.o
