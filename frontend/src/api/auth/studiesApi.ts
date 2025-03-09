import {api} from "@/api/api";

export type StudyDto = {
    id: number;
    name: string;
}

export async function allStudies(): Promise<StudyDto[]> {
    const response = await api.get("/studies");

    return response.data;
}

export async function getTypes(studyId: number): Promise<string[]> {
    const response = await api.get("/studies/" + studyId + "/types")

    return response.data;
}

export async function createStudy(studyDto: StudyDto): Promise<void> {
    const response = await api.post("/studies", studyDto);
}
