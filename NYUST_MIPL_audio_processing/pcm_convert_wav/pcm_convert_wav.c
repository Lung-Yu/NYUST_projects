#include <stdio.h>
#include <stdlib.h>
#include <string.h> // memcpy

#include "wave.hpp"

#define TRUE 1
#define FALSE 0

int get_file_size(FILE **file_ptr);

int openfile(char *filename, FILE **f_ptr);
int create_filename(char *filename, FILE **file_ptr);

void write_wave_header(FILE **file_ptr, int available_data_size);
void write_wave_data(FILE** src_file_ptr,FILE** dst_file_ptr);

extern int pcm2wav(char *src_finetname,char *dst_filename);

int main(int argc, char *argv[])
{
	//filename
    char dst_file_name[] = "test.wav";
    char src_filename[] = "test6.pcm";

    if(!pcm2wav(src_filename,dst_file_name))
        exit(1);
	
    return 0;
}

int pcm2wav(char *src_filename,char *dst_file_name){
    //open file resouce
    //printf("open file\n");
    FILE *src_file_ptr, *dst_file_ptr;
    if (!openfile(src_filename, &src_file_ptr))	return FALSE;
    if (!create_filename(dst_file_name, &dst_file_ptr))	return FALSE;

	//convert file process
    //printf("start convert process\n");
    int data_size = get_file_size(&src_file_ptr);
    write_wave_header(&dst_file_ptr, data_size);
    write_wave_data(&src_file_ptr,&dst_file_ptr);

	//release file point
    //printf("release resouce\n");
    fclose(dst_file_ptr);
    fclose(src_file_ptr);
	
	//finish info.
    //printf("file convert done.\n");

    return TRUE;
}

int create_filename(char *filename, FILE **file_ptr)
{
    //create new file
    *file_ptr = fopen(filename, "wb+");
    if (*file_ptr == NULL)
    {
        printf("create wav file error\n");
        return FALSE;
    }
    return TRUE;
}

int openfile(char *filename, FILE **f_ptr)
{
    // open file
    if ((*f_ptr = fopen(filename, "rb")) == NULL) 
    {
        printf("open pcm file %s error\n", filename);
        return FALSE;
    }
    return TRUE;
}

int get_file_size(FILE **file_ptr)
{
    fseek(*file_ptr, 0, SEEK_END); // seek to end of file
    long size = ftell(*file_ptr);  // get current file pointer
    fseek(*file_ptr, 0, SEEK_SET); // seek back to beginning of file
    return (int)size;
}

/*************************************
* write_wave_header() configuration: *
* - 1 channels,                      *
* - frequency 8000 Hz.               *
**************************************/
void write_wave_header(FILE **file_ptr, int available_data_size)
{
    int file_length = available_data_size + 44 - 8;
    int fmt_header_length = 16;
    short bit_per_sample = 16;
    short channels = 1;
    short formatTag = 0x0001;
    int samplesPerSec = 8000;
    short blockAlign = (short)(channels * samplesPerSec / 8);
    int avgBytesPerSec = blockAlign * samplesPerSec;
    int dataHdrLeth = available_data_size;

    WAVE_HEADER header;

    memcpy(header.RIFF_CHUNK_ID, fChunkID, 4);
    header.RIFF_CHUNK_SIZE = file_length;
    memcpy(header.RIFF_FORMAT, fFormat, 4);
    memcpy(header.FMT_SUB_CHUNK1_ID, fSubchunk1ID, 4);
    header.FMT_SUB_CHUNK1_SIZE = 16;
    header.FMT_AUDIO_FORMAT = 0x0001;
    header.FMT_NUM_CHANNELS = channels;
    header.FMT_SAMPLE_RATE = 8000;
    header.FMT_BYTE_RATE = avgBytesPerSec;
    header.FMT_BLOCK_ALIGN = blockAlign;
    header.FMT_BITS_PER_SAMPLE = 16;
    memcpy(header.DATA_SUB_CHUNK2_ID, fSubchunk2ID, 4);
    header.DATA_SUB_CHUNK2_SIZE = available_data_size;

    fwrite(&header, sizeof(WAVE_HEADER), 1, *file_ptr);
}


void write_wave_data(FILE** src_file_ptr,FILE** dst_file_ptr){
	unsigned short m_pcmData;
    fread(&m_pcmData, sizeof(unsigned short), 1, *src_file_ptr);
    while (!feof(*src_file_ptr))
    {
        fwrite(&m_pcmData, sizeof(unsigned short), 1, *dst_file_ptr);
        fread(&m_pcmData, sizeof(unsigned short), 1, *src_file_ptr);
    }
}
