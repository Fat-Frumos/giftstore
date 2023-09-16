// import {FilterPipe} from './filter.pipe';
// import {Certificate} from "../model/Certificate";
//
// describe('FilterPipe', () => {
//   let pipe: FilterPipe;
//   let mockCertificates: Certificate[];
//
//   beforeEach(() => {
//     pipe = new FilterPipe();
//     mockCertificates = [{
//       id: '1',
//       name: 'test',
//       description: 'test',
//       shortDescription: 'test',
//       company: 'test',
//       price: 100,
//       duration: 1,
//       createDate: new Date(),
//       lastUpdate: new Date(),
//       path: 'test',
//       tags: new Set([{id: 1, name: 'test'}])
//     }];
//   });
//
//   it('create an instance', () => {
//     expect(pipe).toBeTruthy();
//   });
//
//   it('should filter certificates', () => {
//     const mockCriteria = {name: 'test', tag: 'test'};
//
//     const result = pipe.transform(mockCertificates, mockCriteria);
//
//     expect(result).toEqual(mockCertificates);
//   });
//
//   it('should return all certificates if no criteria is provided', () => {
//
//     const result = pipe.transform(mockCertificates, {name: '', tag: ''});
//
//     expect(result).toEqual(mockCertificates);
//   });
// });
