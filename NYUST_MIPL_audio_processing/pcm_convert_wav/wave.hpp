typedef struct
{
    //The 'RIFF' chunck descriptor
    unsigned char RIFF_CHUNK_ID[4];
    unsigned int RIFF_CHUNK_SIZE;
    unsigned char RIFF_FORMAT[4];

    //The 'fmt' sub-chunck
    unsigned char FMT_SUB_CHUNK1_ID[4];
    unsigned int FMT_SUB_CHUNK1_SIZE;
    unsigned short FMT_AUDIO_FORMAT;
    unsigned short FMT_NUM_CHANNELS;
    unsigned int FMT_SAMPLE_RATE;
    unsigned int FMT_BYTE_RATE;
    unsigned short FMT_BLOCK_ALIGN;
    unsigned short FMT_BITS_PER_SAMPLE;

    //The 'data' sub-chunk
    unsigned char DATA_SUB_CHUNK2_ID[4];
    unsigned int DATA_SUB_CHUNK2_SIZE;
} WAVE_HEADER;

const unsigned char fChunkID[] = {'R', 'I', 'F', 'F'};
const unsigned char fFormat[] = {'W', 'A', 'V', 'E'};
const unsigned char fSubchunk1ID[] = {'f', 'm', 't', ' '};
const unsigned char fSubchunk2ID[] = {'d', 'a', 't', 'a'};

